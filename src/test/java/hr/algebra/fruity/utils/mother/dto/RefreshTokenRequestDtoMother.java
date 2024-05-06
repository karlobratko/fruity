package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.RefreshTokenRequestDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenRequestDtoMother {

  public static RefreshTokenRequestDto.RefreshTokenRequestDtoBuilder complete() {
    return RefreshTokenRequestDto.builder()
      .refreshToken(Constants.instanceRefreshToken);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final UUID instanceRefreshToken = UUID.randomUUID();

  }

}