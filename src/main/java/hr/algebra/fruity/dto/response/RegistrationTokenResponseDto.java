package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationTokenResponseDto(
  Long id,
  UUID uuid,
  LocalDateTime createDateTime,
  LocalDateTime expireDateTime,
  LocalDateTime confirmDateTime
) {
}
