package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.ArcodeParcel;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedUpdateRowClusterRequestDto(
  String name,
  ArcodeParcel arcodeParcel,
  BigDecimal surface
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String name = "name";

    public static final String arcodeParcel = "arcodeParcel";

    public static final String surface = "surface";

  }

}