package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.model.UnitOfMeasure;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedCreateRealisationHarvestRequestDto(
  FruitCultivar fruitCultivar,
  HarvestedFruitClass fruitClass,
  BigDecimal quantity,
  UnitOfMeasure unitOfMeasure,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String fruitCultivar = "fruitCultivar";

    public static final String fruitClass = "fruitClass";

    public static final String quantity = "quantity";

    public static final String unitOfMeasure = "unitOfMeasure";

    public static final String note = "note";

  }

}
