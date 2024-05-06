package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRowClusterRequestDto;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.utils.mother.model.ArcodeParcelMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateRowClusterRequestDtoMother {

  public static JoinedCreateRowClusterRequestDto.JoinedCreateRowClusterRequestDtoBuilder complete() {
    return JoinedCreateRowClusterRequestDto.builder()
      .arcodeParcel(Constants.instanceArcodeParcel)
      .name(Constants.instanceName)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final ArcodeParcel instanceArcodeParcel = ArcodeParcelMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

  }

}