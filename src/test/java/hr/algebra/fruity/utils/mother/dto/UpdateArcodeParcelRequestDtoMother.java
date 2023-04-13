package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateArcodeParcelRequestDtoMother {

  public static UpdateArcodeParcelRequestDto.UpdateArcodeParcelRequestDtoBuilder complete() {
    return UpdateArcodeParcelRequestDto.builder()
      .cadastralParcelFk(Constants.instanceCadastralParcelFk)
      .name(Constants.instanceName)
      .arcode(Constants.instanceArcode)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final Long instanceCadastralParcelFk = 1L;

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final Integer instanceArcode = 1;

  }

}