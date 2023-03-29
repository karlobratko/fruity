package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.AuthenticationResponseDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationResponseDtoMother {

  public static AuthenticationResponseDto.AuthenticationResponseDtoBuilder complete() {
    return AuthenticationResponseDto.builder()
      .refreshToken(Constants.instanceRefreshToken)
      .accessToken(Constants.instanceAccessToken)
      .tokenType(Constants.instanceTokenType);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final UUID instanceRefreshToken = UUID.randomUUID();

    public static final String instanceAccessToken = "accessToken";

    public static final String instanceTokenType = "tokenType";

  }

}