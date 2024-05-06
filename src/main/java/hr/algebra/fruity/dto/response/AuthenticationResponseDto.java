package hr.algebra.fruity.dto.response;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record AuthenticationResponseDto(
  String accessToken,
  UUID refreshToken,
  String tokenType
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String accessToken = "accessToken";

    public static final String refreshToken = "refreshToken";

    public static final String tokenType = "tokenType";

  }

}
