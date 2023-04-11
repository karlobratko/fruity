package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCadastralParcelRequestDtoMother {

  public static UpdateCadastralParcelRequestDto.UpdateCadastralParcelRequestDtoBuilder complete() {
    return UpdateCadastralParcelRequestDto.builder()
      .name(Constants.instanceName)
      .ownershipStatusFk(Constants.instanceOwnershipStatusFk)
      .cadastralMunicipalityFk(Constants.instanceCadastralMunicipalityFk)
      .surface(Constants.instanceSurface)
      .cadastralNumber(Constants.instanceCadastralNumber);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final Integer instanceOwnershipStatusFk = 1;

    public static final Integer instanceCadastralMunicipalityFk = 1;

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final String instanceCadastralNumber = "cadastralNumber";

  }

}