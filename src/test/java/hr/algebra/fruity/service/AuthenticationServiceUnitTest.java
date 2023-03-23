package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import hr.algebra.fruity.exception.BadRequestException;
import hr.algebra.fruity.exception.NotFoundException;
import hr.algebra.fruity.exception.RegistrationTokenExpiredException;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.RegistrationToken;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.repository.RegistrationTokenRepository;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.impl.JwtAuthenticationService;
import hr.algebra.fruity.utils.mother.dto.FullEmployeeResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.LoginRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.RegisterRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.RegistrationTokenResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.EmployeeMother;
import hr.algebra.fruity.utils.mother.model.RegistrationTokenMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticationService Unit Test")
@SuppressWarnings("static-access")
class AuthenticationServiceUnitTest {

  @InjectMocks
  JwtAuthenticationService authenticationService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtTokenService jwtTokenService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RegistrationTokenRepository registrationTokenRepository;

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private EmailComposerService emailComposerService;

  @Mock
  private EmailSenderService emailSenderService;

  @Nested
  @DisplayName("... WHEN register is called")
  class WHEN_register {

    @Test
    @DisplayName("GIVEN RegisterRequestDto " +
      "... THEN FullEmployeeResponseDto is returned")
    public void GIVEN_RegisterRequestDto_THEN_FullEmployeeResponseDto() {
      // GIVEN
      // ... RegisterRequestDto
      val requestDto = RegisterRequestDtoMother.completeAndBuilt();
      // ... ConversionService successfully converts from RegisterRequestDto to User
      val user = UserMother.complete().build();
      given(conversionService.convert(requestDto, User.class)).willReturn(user);
      // ... UserRepository successfully saves User
      given(userRepository.save(user)).willReturn(user);
      // ... RegistrationTokenRepository successfully saves RegistrationToken
      val registrationToken = RegistrationTokenMother.complete().build();
      given(registrationTokenRepository.save(any(RegistrationToken.class))).willReturn(registrationToken);
      // ... ConversionService successfully converts from RegisterRequestDto to Employee
      val employee = EmployeeMother.complete().user(null).registrationToken(null).build();
      given(conversionService.convert(requestDto, Employee.class)).willReturn(employee);
      // ... EmployeeRepository successfully saves Employee
      given(employeeRepository.save(employee)).willReturn(employee);
      // ... ConversionService successfully converts from Employee to FullEmployeeResponseDto
      val expectedResponseDto = FullEmployeeResponseDtoMother.completeAndBuilt();
      given(conversionService.convert(employee, FullEmployeeResponseDto.class)).willReturn(expectedResponseDto);

      // WHEN
      // ... register is called
      val responseDto = authenticationService.register(requestDto);

      // THEN
      // ... FullEmployeeResponseDto is returned
      and.then(employee).satisfies(it -> {
        and.then(it.getUser()).isNotNull();
        and.then(it.getRegistrationToken()).isNotNull();
      });
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("... WHEN confirmRegistration is called")
  class WHEN_confirmRegistration {

    @Test
    @DisplayName("GIVEN invalid UUID " +
      "... THEN NotFoundException is thrown")
    public void GIVEN_invalidUUID_THEN_NotFoundException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository fails to find RegistrationToken by UUID
      given(registrationTokenRepository.findByUuid(uuid)).willReturn(Optional.empty());

      // WHEN
      // ... confirmRegistration is called
      when(() -> authenticationService.confirmRegistration(uuid));

      // THEN
      // ... NotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Registracijski token nije važeći.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID and confirmed RegistrationToken " +
      "... THEN BadRequestException is thrown")
    public void GIVEN_UUIDAndConfirmed_THEN_BadRequestException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID which is confirmed
      val registrationToken = RegistrationTokenMother.complete().confirmDateTime(LocalDateTime.now()).build();
      given(registrationTokenRepository.findByUuid(uuid)).willReturn(Optional.of(registrationToken));

      // WHEN
      // ... confirmRegistration is called
      when(() -> authenticationService.confirmRegistration(uuid));

      // THEN
      // ... BadRequestException is thrown
      and.then(caughtException())
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Registracijski token je već bio potvrđen.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID and expired RegistrationToken " +
      "... THEN RegistrationTokenExpiredException is thrown")
    public void GIVEN_UUIDAndExpired_THEN_RegistrationTokenExpiredException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID which is expired
      val registrationToken = RegistrationTokenMother.complete()
        .createDateTime(LocalDateTime.now().minusMinutes(30))
        .expireDateTime(LocalDateTime.now().minusMinutes(15))
        .build();
      given(registrationTokenRepository.findByUuid(uuid)).willReturn(Optional.of(registrationToken));

      // WHEN
      // ... confirmRegistration is called
      when(() -> authenticationService.confirmRegistration(uuid));

      // THEN
      // ... RegistrationTokenExpiredException is thrown
      and.then(caughtException())
        .isInstanceOf(RegistrationTokenExpiredException.class)
        .hasMessage("Registracijski token je isteknuo.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID " +
      "... THEN RegistrationTokenResponseDto is returned")
    public void GIVEN_UUID_THEN_RegistrationTokenResponseDto() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID
      val employee = EmployeeMother.complete().enabled(false).locked(true).build();
      val registrationToken = RegistrationTokenMother.complete()
        .employee(employee)
        .build();
      given(registrationTokenRepository.findByUuid(uuid)).willReturn(Optional.of(registrationToken));
      // ... RegistrationTokenRepository successfully saves RegistrationToken
      val confirmedRegistrationToken = RegistrationTokenMother.complete().build();
      confirmedRegistrationToken.confirm();
      given(registrationTokenRepository.save(registrationToken)).willReturn(confirmedRegistrationToken);
      // ... EmployeeRepository successfully saves Employee
      given(employeeRepository.save(employee)).willReturn(employee);
      // ... ConversionService successfully converts from RegistrationToken to RegistrationTokenResponseDto
      val expectedResponseDto = RegistrationTokenResponseDtoMother.completeAndBuilt();
      given(conversionService.convert(registrationToken, RegistrationTokenResponseDto.class)).willReturn(expectedResponseDto);

      // WHEN
      // ... confirmRegistration is called
      val responseDto = authenticationService.confirmRegistration(uuid);

      // THEN
      // ... NotFoundException is thrown
      and.then(registrationToken.isConfirmed()).isTrue();
      and.then(employee).satisfies(it -> {
        and.then(it.isEnabled()).isTrue();
        and.then(it.isLocked()).isFalse();
      });
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("... WHEN resendRegistrationToken is called")
  class WHEN_resendRegistrationToken {

    @Test
    @DisplayName("GIVEN invalid UUID " +
      "... THEN NotFoundException is thrown")
    public void GIVEN_invalidUUID_THEN_NotFoundException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository fails to find RegistrationToken by UUID
      given(registrationTokenRepository.findByUuid(uuid)).willReturn(Optional.empty());

      // WHEN
      // ... confirmRegistration is called
      when(() -> authenticationService.resendRegistrationToken(uuid));

      // THEN
      // ... NotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Registracijski token nije važeći.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID " +
      "... THEN RegistrationTokenResponseDto is returned")
    public void GIVEN_UUID_THEN_RegistrationTokenResponseDto() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository fails to find RegistrationToken by UUID
      val employee = EmployeeMother.complete().build();
      val registrationToken = RegistrationTokenMother.complete()
        .employee(employee)
        .createDateTime(LocalDateTime.now().minusMinutes(30))
        .expireDateTime(LocalDateTime.now().minusMinutes(15))
        .build();
      given(registrationTokenRepository.findByUuid(uuid)).willReturn(Optional.of(registrationToken));
      // ... RegistrationTokenRepository successfully saves RegistrationToken
      val resetRegistrationToken = RegistrationTokenMother.complete().build();
      resetRegistrationToken.reset();
      given(registrationTokenRepository.save(registrationToken)).willReturn(resetRegistrationToken);
      // ... ConversionService successfully converts from Employee to FullEmployeeResponseDto
      val expectedResponseDto = RegistrationTokenResponseDtoMother.completeAndBuilt();
      given(conversionService.convert(registrationToken, RegistrationTokenResponseDto.class)).willReturn(expectedResponseDto);

      // WHEN
      // ... confirmRegistration is called
      val responseDto = authenticationService.resendRegistrationToken(uuid);

      // THEN
      // ... NotFoundException is thrown
      and.then(registrationToken).satisfies(it -> {
        and.then(it.getCreateDateTime()).isAfter(LocalDateTime.now().minusSeconds(5));
        and.then(it.getExpireDateTime()).isAfter(LocalDateTime.now().plusMinutes(15).minusSeconds(5));
      });
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("... WHEN login is called")
  class WHEN_login {

    @Test
    @DisplayName("GIVEN invalid LoginRequestDto " +
      "... THEN BadCredentialsException is thrown")
    public void GIVEN_invalidLoginRequestDto_THEN_BadCredentialsException() {
      // GIVEN
      // ... invalid LoginRequestDto
      val requestDto = LoginRequestDtoMother.completeAndBuilt();
      // ... AuthenticationManager fails to authenticate UsernamePasswordAuthenticationToken
      val token = new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password());
      given(authenticationManager.authenticate(token)).willThrow(BadCredentialsException.class);
      // ... EmployeeRepository fails to find Employee by username
      val username = requestDto.username();
      given(employeeRepository.findByUsername(username)).willReturn(Optional.empty());

      // WHEN
      // ... login is called
      when(() -> authenticationService.login(requestDto));

      // THEN
      // ... BadCredentialsException is thrown
      and.then(caughtException())
        .isInstanceOf(BadCredentialsException.class)
        .hasMessage("Nevažeće korisničko ime i lozinka.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN LoginRequestDto and disabled Employee " +
      "... THEN DisabledException is thrown")
    public void GIVEN_LoginRequestDtoAndDisabledEmployee_THEN_DisabledException() {
      // GIVEN
      // ... invalid LoginRequestDto
      val requestDto = LoginRequestDtoMother.completeAndBuilt();
      // ... AuthenticationManager fails to authenticate UsernamePasswordAuthenticationToken
      val token = new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password());
      given(authenticationManager.authenticate(token)).willThrow(DisabledException.class);
      // ... EmployeeRepository fails to find Employee by username
      val username = requestDto.username();
      val employee = EmployeeMother.complete().enabled(false).locked(true).build();
      given(employeeRepository.findByUsername(username)).willReturn(Optional.of(employee));

      // WHEN
      // ... login is called
      when(() -> authenticationService.login(requestDto));

      // THEN
      // ... BadCredentialsException is thrown
      and.then(caughtException())
        .isInstanceOf(DisabledException.class)
        .hasMessage("Korisnički račun nije omogućen.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN LoginRequestDto and locked Employee " +
      "... THEN LockedException is thrown")
    public void GIVEN_LoginRequestDtoAndLockedEmployee_THEN_LockedException() {
      // GIVEN
      // ... invalid LoginRequestDto
      val requestDto = LoginRequestDtoMother.completeAndBuilt();
      // ... AuthenticationManager fails to authenticate UsernamePasswordAuthenticationToken
      val token = new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password());
      given(authenticationManager.authenticate(token)).willThrow(DisabledException.class);
      // ... EmployeeRepository fails to find Employee by username
      val username = requestDto.username();
      val employee = EmployeeMother.complete().enabled(true).locked(true).build();
      given(employeeRepository.findByUsername(username)).willReturn(Optional.of(employee));

      // WHEN
      // ... login is called
      when(() -> authenticationService.login(requestDto));

      // THEN
      // ... BadCredentialsException is thrown
      and.then(caughtException())
        .isInstanceOf(LockedException.class)
        .hasMessage("Korisnički račun je zaključan.")
        .hasNoCause();
    }

  }

}