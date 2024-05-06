package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateArcodeParcelRequestDtoMother {

  public static CreateArcodeParcelRequestDto.CreateArcodeParcelRequestDtoBuilder complete() {
    return CreateArcodeParcelRequestDto.builder()
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
