package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullUnitOfMeasureResponseDto(
  Integer id,
  String abbreviation,
  String name,
  UnitOfMeasureResponseDto base,
  BigDecimal baseMultiplier
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String abbreviation = "abbreviation";

    public static final String name = "name";

    public static final String base = "base";

    public static final String baseMultiplier = "baseMultiplier";

  }

}