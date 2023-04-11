package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CadastralParcelResponseDto(
  Long id,
  String name,
  CadastralMunicipalityResponseDto cadastralMunicipality,
  String cadastralNumber,
  BigDecimal surface,
  String ownershipStatus
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String name = "name";

    public static final String cadastralMunicipality = "cadastralMunicipality";

    public static final String cadastralNumber = "cadastralNumber";

    public static final String surface = "surface";

    public static final String ownershipStatus = "ownershipStatus";

  }

}
