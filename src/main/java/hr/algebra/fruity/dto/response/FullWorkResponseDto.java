package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullWorkResponseDto(
  Long id,
  LocalDateTime startDateTime,
  LocalDateTime endDateTime,
  Boolean finished,
  String note,
  FullWorkTypeResponseDto type
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    private static final String id = "id";

    private static final String startDateTime = "startDateTime";

    private static final String endDateTime = "endDateTime";

    private static final String finished = "finished";

    public static final String note = "note";

    private static final String type = "type";

  }

}
