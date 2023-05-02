package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateRealisationHarvestRequestDto(
  @NotNull(message = "Voćna vrsta je obavezno polje.")
  Integer fruitCultivarFk,
  @NotNull(message = "Klasa voća je obavezno polje.")
  Integer fruitClassFk,
  @NotNull(message = "Količina je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Količina mora biti pozitivan broj ili nula.")
  BigDecimal quantity,
  @NotNull(message = "Mjerna jedinica količine voća je obavezno polje.")
  Integer unitOfMeasureFk,
  @Size(max = 500, message = "Napomena mora biti duljine maksimalno 500 znakova.")
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String fruitCultivarFk = "fruitCultivarFk";

    public static final String fruitClassFk = "fruitClassFk";

    public static final String quantity = "quantity";

    public static final String unitOfMeasureFk = "unitOfMeasureFk";

    public static final String note = "note";

  }

}
