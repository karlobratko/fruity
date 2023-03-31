package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.ConfirmRegistrationRequestDto;
import hr.algebra.fruity.dto.request.LoginMobileRequestDto;
import hr.algebra.fruity.dto.request.LoginRequestDto;
import hr.algebra.fruity.dto.request.RefreshTokenRequestDto;
import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.dto.request.ResendRegistrationRequestDto;
import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;

public interface AuthenticationService {

  void register(RegisterRequestDto requestDto);

  RegistrationTokenResponseDto confirmRegistration(ConfirmRegistrationRequestDto requestDto);

  RegistrationTokenResponseDto resendRegistrationToken(ResendRegistrationRequestDto requestDto);

  AuthenticationResponseDto login(LoginRequestDto requestDto);

  AuthenticationResponseDto refreshToken(RefreshTokenRequestDto requestDto);

  AuthenticationResponseDto loginMobile(LoginMobileRequestDto requestDto);

}
