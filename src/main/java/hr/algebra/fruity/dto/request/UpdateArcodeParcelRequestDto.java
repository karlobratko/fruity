package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record UpdateArcodeParcelRequestDto(
  @Size(min = 3, max = 100, message = "Naziv mora biti duljine minimalno 3 i maksimalno 100 znakova.")
  String name,
  Long cadastralParcelFk,
  @PositiveOrZero(message = "ARKOD mora biti pozitivan broj veći od nule.")
  Integer arcode,
  @DecimalMin(value = "0.00", message = "Površina mora biti pozitivan broj ili nula.")
  BigDecimal surface
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String cadastralParcelFk = "cadastralParcelFk";

    public static final String arcode = "arcode";

    public static final String surface = "surface";

  }

}
