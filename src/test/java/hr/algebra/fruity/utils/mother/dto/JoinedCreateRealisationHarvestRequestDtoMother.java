package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationHarvestRequestDto;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.utils.mother.model.FruitCultivarMother;
import hr.algebra.fruity.utils.mother.model.HarvestedFruitClassMother;
import hr.algebra.fruity.utils.mother.model.UnitOfMeasureMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateRealisationHarvestRequestDtoMother {

  public static JoinedCreateRealisationHarvestRequestDto.JoinedCreateRealisationHarvestRequestDtoBuilder complete() {
    return JoinedCreateRealisationHarvestRequestDto.builder()
      .fruitCultivar(Constants.instanceFruitCultivar)
      .fruitClass(Constants.instanceHarvestedFruitClass)
      .quantity(Constants.instanceQuantity)
      .unitOfMeasure(Constants.instanceUnitOfMeasure)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final FruitCultivar instanceFruitCultivar = FruitCultivarMother.complete().build();

    public static final HarvestedFruitClass instanceHarvestedFruitClass = HarvestedFruitClassMother.complete().build();

    public static final BigDecimal instanceQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final String instanceNote = "note";

  }

}
