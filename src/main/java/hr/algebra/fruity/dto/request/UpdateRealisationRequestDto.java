package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
public record UpdateRealisationRequestDto(
  Long workFk,
  Long employeeFk,
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime startDateTime,
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime endDateTime,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String workFk = "workFk";

    public static final String employeeFk = "employeeFk";

    public static final String startDateTime = "startDateTime";

    public static final String endDateTime = "endDateTime";

    public static final String note = "note";

  }

}
