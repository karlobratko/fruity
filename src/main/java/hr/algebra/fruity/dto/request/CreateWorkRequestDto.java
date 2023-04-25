package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
public record CreateWorkRequestDto(
  @NotNull(message = "Planirani datum početka rada je obavezno polje.")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime startDateTime,
  @NotNull(message = "Planirani datum završetka rada je obavezno polje.")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime endDateTime,
  Boolean finished,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note,
  @NotNull(message = "Vrsta rada je obavezno polje.")
  Integer typeFk
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String startDateTime = "startDateTime";

    public static final String endDateTime = "endDateTime";

    public static final String finished = "finished";

    public static final String note = "note";

    public static final String typeFk = "typeFk";

  }

}
