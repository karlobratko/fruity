package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowClusterResponseDtoMother {

  public static RowClusterResponseDto.RowClusterResponseDtoBuilder complete() {
    return RowClusterResponseDto.builder()
      .id(Constants.instanceId)
      .arcodeParcelFk(Constants.instanceArcodeParcelFk)
      .name(Constants.instanceName)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final Long instanceArcodeParcelFk = 1L;

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

  }

}
