package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedUpdateCadastralParcelRequestDto(
  String name,
  CadastralMunicipality cadastralMunicipality,
  String cadastralNumber,
  BigDecimal surface,
  CadastralParcelOwnershipStatus ownershipStatus
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String cadastralMunicipality = "cadastralMunicipality";

    public static final String cadastralNumber = "cadastralNumber";

    public static final String surface = "surface";

    public static final String ownershipStatus = "ownershipStatus";

  }

}
