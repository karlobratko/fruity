package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullArcodeParcelResponseDtoMother {

  public static FullArcodeParcelResponseDto.FullArcodeParcelResponseDtoBuilder complete() {
    return FullArcodeParcelResponseDto.builder()
      .id(Constants.instanceId)
      .cadastralParcel(Constants.instanceCadastralParcel)
      .name(Constants.instanceName)
      .arcode(Constants.instanceArcode)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final CadastralParcelResponseDto instanceCadastralParcel = CadastralParcelResponseDtoMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final Integer instanceArcode = 1;

  }

}
