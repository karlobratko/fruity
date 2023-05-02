package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullRealisationHarvestResponseDto(
  RealisationResponseDto realisation,
  FruitCultivarResponseDto fruitCultivar,
  HarvestedFruitClassResponseDto fruitClass,
  BigDecimal quantity,
  UnitOfMeasureResponseDto unitOfMeasure,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {
    private static final String realisation = "realisation";

    private static final String fruitCultivar = "fruitCultivar";

    private static final String fruitClass = "fruitClass";

    private static final String quantity = "quantity";

    private static final String unitOfMeasure = "unitOfMeasure";

    public static final String note = "note";

  }

}
