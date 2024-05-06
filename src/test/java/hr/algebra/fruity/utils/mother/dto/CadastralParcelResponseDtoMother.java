package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CadastralParcelResponseDtoMother {

  public static CadastralParcelResponseDto.CadastralParcelResponseDtoBuilder complete() {
    return CadastralParcelResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .ownershipStatus(Constants.instanceOwnershipStatus)
      .cadastralMunicipality(Constants.instanceCadastralMunicipality)
      .surface(Constants.instanceSurface)
      .cadastralNumber(Constants.instanceCadastralNumber);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final String instanceOwnershipStatus = "ownershipStatus";

    public static final CadastralMunicipalityResponseDto instanceCadastralMunicipality = CadastralMunicipalityResponseDtoMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final String instanceCadastralNumber = "cadastralNumber";

  }

}
