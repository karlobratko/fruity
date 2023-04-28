package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateCadastralParcelRequestDto;
import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.utils.mother.model.CadastralMunicipalityMother;
import hr.algebra.fruity.utils.mother.model.CadastralParcelOwnershipStatusMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateCadastralParcelRequestDtoMother {

  public static JoinedCreateCadastralParcelRequestDto.JoinedCreateCadastralParcelRequestDtoBuilder complete() {
    return JoinedCreateCadastralParcelRequestDto.builder()
      .name(Constants.instanceName)
      .ownershipStatus(Constants.instanceOwnershipStatus)
      .cadastralMunicipality(Constants.instanceCadastralMunicipality)
      .surface(Constants.instanceSurface)
      .cadastralNumber(Constants.instanceCadastralNumber);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final CadastralParcelOwnershipStatus instanceOwnershipStatus = CadastralParcelOwnershipStatusMother.complete().build();

    public static final CadastralMunicipality instanceCadastralMunicipality = CadastralMunicipalityMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final String instanceCadastralNumber = "cadastralNumber";

  }

}