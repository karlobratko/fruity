package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.LoginRequestDto;
import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.dto.request.ResendRegistrationRequestDto;
import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import java.util.UUID;

public interface AuthenticationService {

  FullEmployeeResponseDto register(RegisterRequestDto requestDto);

  RegistrationTokenResponseDto confirmRegistration(UUID uuid);

  RegistrationTokenResponseDto resendRegistrationToken(UUID uuid, ResendRegistrationRequestDto requestDto);

  AuthenticationResponseDto login(LoginRequestDto requestDto);

}
