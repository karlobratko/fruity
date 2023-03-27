package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RegistrationTokenResponseDto(
  Long id,
  UUID uuid,
  LocalDateTime createDateTime,
  LocalDateTime expireDateTime,
  LocalDateTime confirmDateTime
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String createDateTime = "createDateTime";

    public static final String expireDateTime = "expireDateTime";

    public static final String confirmDateTime = "confirmDateTime";

  }

}
