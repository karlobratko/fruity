package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.EmployeeToAuthenticationResponseDtoConverter;
import hr.algebra.fruity.converter.RegisterRequestDtoToEmployeeConverter;
import hr.algebra.fruity.converter.RegisterRequestDtoToUserConverter;
import hr.algebra.fruity.converter.RegistrationTokenToRegistrationTokenResponseDtoConverter;
import hr.algebra.fruity.dto.request.ConfirmRegistrationRequestDto;
import hr.algebra.fruity.dto.request.LoginMobileRequestDto;
import hr.algebra.fruity.dto.request.LoginRequestDto;
import hr.algebra.fruity.dto.request.RefreshTokenRequestDto;
import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.dto.request.ResendRegistrationRequestDto;
import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import hr.algebra.fruity.exception.UsernameNotFoundException;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.RegistrationToken;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.AuthenticationService;
import hr.algebra.fruity.service.EmailComposerService;
import hr.algebra.fruity.service.EmailSenderService;
import hr.algebra.fruity.service.MobileTokenService;
import hr.algebra.fruity.service.RefreshTokenService;
import hr.algebra.fruity.service.RegistrationTokenService;
import hr.algebra.fruity.validator.RegisterRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService implements AuthenticationService {

  private final RegisterRequestDtoToUserConverter fromRegisterRequestDtoToUserConverter;

  private final RegisterRequestDtoToEmployeeConverter fromRegisterRequestDtoToEmployeeConverter;

  private final RegistrationTokenToRegistrationTokenResponseDtoConverter toRegistrationTokenResponseDtoConverter;

  private final EmployeeToAuthenticationResponseDtoConverter toAuthenticationResponseDtoConverter;

  private final RegisterRequestDtoValidator registerRequestDtoValidator;

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final EmployeeRepository employeeRepository;

  private final EmailComposerService emailComposerService;

  private final EmailSenderService emailSenderService;

  private final RegistrationTokenService registrationTokenService;

  private final RefreshTokenService refreshTokenService;

  private final MobileTokenService mobileTokenService;

  @Override
  @Transactional
  public void register(RegisterRequestDto requestDto) {
    registerRequestDtoValidator.validate(requestDto);

    val user = userRepository.save(
      Objects.requireNonNull(fromRegisterRequestDtoToUserConverter.convert(requestDto))
    );

    val registrationToken = registrationTokenService.createRegistrationToken();

    val employee = Objects.requireNonNull(fromRegisterRequestDtoToEmployeeConverter.convert(requestDto));
    employee.setUser(user);
    employee.setRegistrationToken(registrationToken);
    employee.setRefreshToken(refreshTokenService.createRefreshToken());
    employee.setMobileToken(mobileTokenService.createMobileToken());
    employeeRepository.save(employee);

    composeAndSendConfirmRegistrationEmail(employee, registrationToken, requestDto.confirmRegistrationUrl());
  }

  @Override
  @Transactional
  public RegistrationTokenResponseDto confirmRegistration(ConfirmRegistrationRequestDto requestDto) {
    val registrationToken = registrationTokenService.confirmRegistrationToken(requestDto.registrationToken());

    val employee = registrationToken.getEmployee();
    employee.activate();
    employeeRepository.save(employee);

    return toRegistrationTokenResponseDtoConverter.convert(registrationToken);
  }

  @Override
  @Transactional
  public RegistrationTokenResponseDto resendRegistrationToken(ResendRegistrationRequestDto requestDto) {
    val registrationToken = registrationTokenService.refreshRegistrationToken(requestDto.registrationToken());

    composeAndSendConfirmRegistrationEmail(registrationToken.getEmployee(), registrationToken, requestDto.confirmRegistrationUrl());

    return toRegistrationTokenResponseDtoConverter.convert(registrationToken);
  }

  @Override
  @Transactional
  public AuthenticationResponseDto login(LoginRequestDto requestDto) {
    try {
      val authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          requestDto.username(),
          requestDto.password()
        )
      );

      val employee = employeeRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new UsernameNotFoundException(authentication.getName()));

      employee.setRefreshToken(refreshTokenService.refreshRefreshToken(employee.getRefreshToken().getUuid()));

      return toAuthenticationResponseDtoConverter.convert(employee);
    } catch (AuthenticationException e) {
      employeeRepository.findByUsername(requestDto.username())
        .ifPresentOrElse(
          employee -> {
            if (!employee.isEnabled())
              throw new DisabledException("Korisnički račun nije omogućen.");

            if (employee.isLocked())
              throw new LockedException("Korisnički račun je zaključan.");

            throw new BadCredentialsException("Nevažeća lozinka.");
          },
          () -> {
            throw new BadCredentialsException("Korisničko ime %s nije registrirano.".formatted(requestDto.username()));
          }
        );

      throw new IllegalStateException();
    }
  }

  @Override
  public AuthenticationResponseDto loginMobile(LoginMobileRequestDto requestDto) {
    val mobileToken = mobileTokenService.verifyMobileToken(requestDto.mobileToken());

    return toAuthenticationResponseDtoConverter.convert(mobileToken.getEmployee());
  }

  @Override
  @Transactional
  public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto requestDto) {
    val refreshToken = refreshTokenService.verifyRefreshToken(requestDto.refreshToken());

    return toAuthenticationResponseDtoConverter.convert(refreshToken.getEmployee());
  }

  private void composeAndSendConfirmRegistrationEmail(Employee employee, RegistrationToken registrationToken, String registrationUrl) {
    emailSenderService.send(
      emailComposerService.composeConfirmRegistrationEmail(
        employee,
        registrationUrl,
        registrationToken.getUuid()
      )
    );
  }

}
