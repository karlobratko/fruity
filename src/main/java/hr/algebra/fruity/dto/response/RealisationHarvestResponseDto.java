package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RealisationHarvestResponseDto(
  Long realisationFk,
  FruitCultivarResponseDto fruitCultivar,
  HarvestedFruitClassResponseDto fruitClass,
  BigDecimal quantity,
  UnitOfMeasureResponseDto unitOfMeasure
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    private static final String realisationFk = "realisationFk";

    private static final String fruitCultivar = "fruitCultivar";

    private static final String fruitClass = "fruitClass";

    private static final String quantity = "quantity";

    private static final String unitOfMeasure = "unitOfMeasure";


  }

}
