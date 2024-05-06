package hr.algebra.fruity.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record UpdateCadastralParcelRequestDto(
  @Size(min = 3, max = 100, message = "Naziv mora biti duljine minimalno 3 i maksimalno 100 znakova.")
  String name,
  Integer cadastralMunicipalityFk,
  @Size(max = 15, message = "Katastarski broj biti duljine maksimalno 15 znakova.")
  String cadastralNumber,
  @DecimalMin(value = "0.00", message = "Cijena po satu mora biti pozitivan broj ili nula.")
  BigDecimal surface,
  Integer ownershipStatusFk
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String cadastralMunicipalityFk = "cadastralMunicipalityFk";

    public static final String cadastralNumber = "cadastralNumber";

    public static final String surface = "surface";

    public static final String ownershipStatusFk = "ownershipStatusFk";

  }

}
