package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateRowClusterRequestDto(
  @NotNull(message = "Naziv je obavezno polje.")
  @Size(min = 3, max = 100, message = "Naziv mora biti duljine minimalno 3 i maksimalno 100 znakova.")
  String name,
  @NotNull(message = "ARKOD parcela je obavezno polje.")
  Long arcodeParcelFk,
  @NotNull(message = "Površina je obavezno polje.")
  @DecimalMin(value = "0.00", message = "Površina mora biti pozitivan broj ili nula.")
  BigDecimal surface
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String arcodeParcelFk = "arcodeParcelFk";

    public static final String surface = "surface";

  }

}