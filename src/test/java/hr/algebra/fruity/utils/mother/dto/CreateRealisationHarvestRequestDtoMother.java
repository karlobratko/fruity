package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateRealisationHarvestRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRealisationHarvestRequestDtoMother {

  public static CreateRealisationHarvestRequestDto.CreateRealisationHarvestRequestDtoBuilder complete() {
    return CreateRealisationHarvestRequestDto.builder()
      .fruitCultivarFk(Constants.instanceFruitCultivarFk)
      .fruitClassFk(Constants.instanceHarvestedFruitClassFk)
      .quantity(Constants.instanceQuantity)
      .unitOfMeasureFk(Constants.instanceUnitOfMeasureFk)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceFruitCultivarFk = 1;

    public static final Integer instanceHarvestedFruitClassFk = 1;

    public static final BigDecimal instanceQuantity = BigDecimal.ONE;

    public static final Integer instanceUnitOfMeasureFk = 1;

    public static final String instanceNote = "note";

  }

}
