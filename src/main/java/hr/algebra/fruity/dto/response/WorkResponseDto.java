package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record WorkResponseDto(
  Long id,
  LocalDateTime startDateTime,
  LocalDateTime endDateTime,
  Boolean finished,
  WorkTypeResponseDto type
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    private static final String id = "id";

    private static final String startDateTime = "startDateTime";

    private static final String endDateTime = "endDateTime";

    private static final String finished = "finished";

    private static final String type = "type";

  }

}
