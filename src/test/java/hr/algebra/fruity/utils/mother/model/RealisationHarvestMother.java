package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.HarvestedFruitClass;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationHarvest;
import hr.algebra.fruity.model.UnitOfMeasure;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealisationHarvestMother {

  public static RealisationHarvest.RealisationHarvestBuilder complete() {
    return RealisationHarvest.builder()
      .id(Constants.instanceId)
      .realisation(Constants.instanceRealisation)
      .fruitCultivar(Constants.instanceFruitCultivar)
      .harvestedFruitClass(Constants.instanceHarvestedFruitClass)
      .quantity(Constants.instanceQuantity)
      .unitOfMeasure(Constants.instanceUnitOfMeasure)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationHarvest.RealisationHarvestId instanceId = new RealisationHarvest.RealisationHarvestId(1L, 1, 1);

    public static final Realisation instanceRealisation = RealisationMother.complete().build();

    public static final FruitCultivar instanceFruitCultivar = FruitCultivarMother.complete().build();

    public static final HarvestedFruitClass instanceHarvestedFruitClass = HarvestedFruitClassMother.complete().build();

    public static final BigDecimal instanceQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final String instanceNote = "note";

  }

}
