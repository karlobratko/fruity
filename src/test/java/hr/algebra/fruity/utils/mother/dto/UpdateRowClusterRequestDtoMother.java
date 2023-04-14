package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRowClusterRequestDtoMother {

  public static UpdateRowClusterRequestDto.UpdateRowClusterRequestDtoBuilder complete() {
    return UpdateRowClusterRequestDto.builder()
      .arcodeParcelFk(Constants.instanceArcodeParcelFk)
      .name(Constants.instanceName)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final Long instanceArcodeParcelFk = 1L;

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

  }

}
