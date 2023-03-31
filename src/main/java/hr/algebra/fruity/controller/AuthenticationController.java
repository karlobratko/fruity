package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.ConfirmRegistrationRequestDto;
import hr.algebra.fruity.dto.request.LoginMobileRequestDto;
import hr.algebra.fruity.dto.request.LoginRequestDto;
import hr.algebra.fruity.dto.request.RefreshTokenRequestDto;
import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.dto.request.ResendRegistrationRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import hr.algebra.fruity.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationController.Mappings.requestMapping)
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping(Mappings.registerPostMapping)
  public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequestDto requestDto) {
    authenticationService.register(requestDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        ApiResponse.created(
          "Registracija uspješna."
        )
      );
  }

  @PostMapping(Mappings.confirmRegistrationGetMapping)
  public ResponseEntity<ApiResponse<RegistrationTokenResponseDto>> confirmRegistration(@Valid @RequestBody ConfirmRegistrationRequestDto requestDto) {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          authenticationService.confirmRegistration(requestDto),
          "Registracijski token uspješno potvrđen."
        )
      );
  }

  @PostMapping(Mappings.resendRegistrationTokenPostMapping)
  public ResponseEntity<ApiResponse<RegistrationTokenResponseDto>> resendRegistrationToken(@Valid @RequestBody ResendRegistrationRequestDto requestDto) {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          authenticationService.resendRegistrationToken(requestDto),
          "Registracijski token ponovno poslan."
        )
      );
  }

  @PostMapping(Mappings.loginPostMapping)
  public ResponseEntity<ApiResponse<AuthenticationResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        authenticationService.login(requestDto),
        "Prijava uspješna."
      )
    );
  }

  @PostMapping(Mappings.loginMobilePostMapping)
  public ResponseEntity<ApiResponse<AuthenticationResponseDto>> loginMobile(@Valid @RequestBody LoginMobileRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        authenticationService.loginMobile(requestDto),
        "Prijava uspješna."
      )
    );
  }


  @PostMapping(Mappings.refreshTokenPostMapping)
  public ResponseEntity<ApiResponse<AuthenticationResponseDto>> refreshToken(@Valid @RequestBody RefreshTokenRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        authenticationService.refreshToken(requestDto),
        "Obnova tokena uspješna."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiUserManagementRequestMapping + "/auth";

    public static final String registerPostMapping = "/register";

    public static final String confirmRegistrationGetMapping = "/confirm-registration";

    public static final String resendRegistrationTokenPostMapping = "/resend-registration-token";

    public static final String loginPostMapping = "/login";

    public static final String loginMobilePostMapping = "/login-mobile";

    public static final String refreshTokenPostMapping = "/refresh-token";

  }

}