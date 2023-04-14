package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRowClusterResponseDtoMother {

  public static FullRowClusterResponseDto.FullRowClusterResponseDtoBuilder complete() {
    return FullRowClusterResponseDto.builder()
      .id(Constants.instanceId)
      .arcodeParcel(Constants.instanceArcodeParcel)
      .name(Constants.instanceName)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final ArcodeParcelResponseDto instanceArcodeParcel = ArcodeParcelResponseDtoMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final Integer instanceArcode = 1;

  }

}
