package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.WorkType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedUpdateWorkRequestDto(
  LocalDateTime startDateTime,
  LocalDateTime endDateTime,
  Boolean finished,
  String note,
  WorkType type
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String startDateTime = "startDateTime";

    public static final String endDateTime = "endDateTime";

    public static final String finished = "finished";

    public static final String note = "note";

    public static final String type = "type";

  }

}
