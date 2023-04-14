package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArcodeParcelResponseDtoMother {

  public static ArcodeParcelResponseDto.ArcodeParcelResponseDtoBuilder complete() {
    return ArcodeParcelResponseDto.builder()
      .id(Constants.instanceId)
      .cadastralParcelFk(Constants.instanceCadastralParcelFk)
      .name(Constants.instanceName)
      .arcode(Constants.instanceArcode)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final Long instanceCadastralParcelFk = 1L;

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final Integer instanceArcode = 1;

  }

}