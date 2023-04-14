package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RowClusterResponseDto(
  Long id,
  String name,
  Long arcodeParcelFk,
  BigDecimal surface
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String name = "name";

    public static final String arcodeParcelFk = "arcodeParcelFk";

    public static final String surface = "surface";

  }

}