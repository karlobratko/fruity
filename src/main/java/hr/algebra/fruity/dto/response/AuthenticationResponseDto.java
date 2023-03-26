package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record AuthenticationResponseDto(
  String token
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String token = "token";

  }

}
