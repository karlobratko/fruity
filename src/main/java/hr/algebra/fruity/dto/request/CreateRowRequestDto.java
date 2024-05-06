package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateRowRequestDto(
  @NotNull(message = "Redni broj je obavezno polje.")
  @PositiveOrZero(message = "Redni broj mora biti pozitivan broj ili nula.")
  Integer ordinal,
  @NotNull(message = "Tabla je obavezno polje.")
  Long rowClusterFk,
  @NotNull(message = "Broj sadnica je obavezno polje.")
  @PositiveOrZero(message = "Broj sadnica mora biti pozitivan broj ili nula.")
  Integer numberOfSeedlings,
  @NotNull(message = "Sorta voća je obavezno polje.")
  Integer fruitCultivarFk,
  @NotNull(message = "Godina sadnje je obavezno polje.")
  @Min(value = 1900, message = "Godina sadnje mora biti broj veći od 1990.")
  Integer plantingYear
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String ordinal = "ordinal";

    public static final String rowClusterFk = "rowClusterFk";

    public static final String numberOfSeedlings = "numberOfSeedlings";

    public static final String fruitCultivarFk = "fruitCultivarFk";

    public static final String plantingYear = "plantingYear";

  }

}