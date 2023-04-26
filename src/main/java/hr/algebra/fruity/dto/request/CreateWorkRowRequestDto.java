package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateWorkRowRequestDto(
  @NotNull(message = "Red je obavezno polje.")
  Long rowFk,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String rowFk = "rowFk";

    public static final String note = "note";

  }

}
