package hr.algebra.fruity.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RealisationResponseDto(
  Long id,
  Long workFk,
  EmployeeResponseDto employee,
  LocalDateTime startDateTime,
  LocalDateTime endDateTime
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    private static final String id = "id";

    private static final String work = "work";

    private static final String employee = "employee";

    private static final String startDateTime = "startDateTime";

    private static final String endDateTime = "endDateTime";

  }

}
