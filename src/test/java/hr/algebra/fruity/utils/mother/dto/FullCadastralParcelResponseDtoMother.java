package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullCadastralParcelResponseDtoMother {

  public static FullCadastralParcelResponseDto.FullCadastralParcelResponseDtoBuilder complete() {
    return FullCadastralParcelResponseDto.builder()
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

    public static final CadastralParcelOwnershipStatusResponseDto instanceOwnershipStatus = CadastralParcelOwnershipStatusResponseDtoMother.complete().build();

    public static final FullCadastralMunicipalityResponseDto instanceCadastralMunicipality = FullCadastralMunicipalityResponseDtoMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final String instanceCadastralNumber = "cadastralNumber";

  }

}
