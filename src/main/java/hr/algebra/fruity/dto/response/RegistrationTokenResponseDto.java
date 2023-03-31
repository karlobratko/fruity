package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RegistrationTokenResponseDto(
  UUID registrationToken,
  LocalDateTime expireDateTime
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String registrationToken = "registrationToken";

    public static final String expireDateTime = "expireDateTime";

  }

}
