package hr.algebra.fruity.dto.request.joined;


import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.Work;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedCreateRealisationRequestDto(
  Work work,
  Employee employee,
  LocalDateTime startDateTime,
  LocalDateTime endDateTime,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String work = "work";

    public static final String employee = "employee";

    public static final String startDateTime = "startDateTime";

    public static final String endDateTime = "endDateTime";

    public static final String note = "note";

  }

}
