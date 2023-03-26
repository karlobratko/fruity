package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.LoginRequestDto;
import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.dto.request.ResendRegistrationRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import hr.algebra.fruity.service.AuthenticationService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationController.Constants.requestMapping)
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping(Constants.registerPostMapping)
  public ResponseEntity<ApiResponse<FullEmployeeResponseDto>> register(@Valid @RequestBody RegisterRequestDto requestDto) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        ApiResponse.created(
          authenticationService.register(requestDto),
          "Registracija uspješna."
        )
      );
  }

  @GetMapping(Constants.confirmRegistrationGetMapping)
  public ResponseEntity<ApiResponse<RegistrationTokenResponseDto>> confirmRegistration(@PathVariable UUID uuid) {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          authenticationService.confirmRegistration(uuid),
          "Registracijski token uspješno potvrđen."
        )
      );
  }

  @GetMapping(Constants.resendRegistrationTokenGetMapping)
  public ResponseEntity<ApiResponse<RegistrationTokenResponseDto>> resendRegistrationToken(@PathVariable UUID uuid, @Valid @RequestBody ResendRegistrationRequestDto requestDto) {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          authenticationService.resendRegistrationToken(uuid, requestDto),
          "Registracijski token ponovno poslan."
        )
      );
  }

  @PostMapping(Constants.loginPostMapping)
  public ResponseEntity<ApiResponse<AuthenticationResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        authenticationService.login(requestDto),
        "Prijava uspješna."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String requestMapping = ApplicationConstants.apiUserManagementRequestMapping + "/auth";

    public static final String registerPostMapping = "/register";

    public static final String confirmRegistrationGetMapping = "/confirm-registration/{uuid}";

    public static final String resendRegistrationTokenGetMapping = "/resend-registration-token/{uuid}";

    public static final String loginPostMapping = "/login";

  }

}