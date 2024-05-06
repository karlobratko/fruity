package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullRowClusterResponseDto(
  Long id,
  String name,
  ArcodeParcelResponseDto arcodeParcel,
  BigDecimal surface
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String name = "name";

    public static final String arcodeParcel = "arcodeParcel";

    public static final String surface = "surface";

  }

}