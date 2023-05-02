package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.dto.response.FullRealisationHarvestResponseDto;
import hr.algebra.fruity.dto.response.HarvestedFruitClassResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRealisationHarvestResponseDtoMother {

  public static FullRealisationHarvestResponseDto.FullRealisationHarvestResponseDtoBuilder complete() {
    return FullRealisationHarvestResponseDto.builder()
      .realisation(Constants.instanceRealisation)
      .fruitCultivar(Constants.instanceFruitCultivar)
      .fruitClass(Constants.instanceHarvestedFruitClass)
      .quantity(Constants.instanceQuantity)
      .unitOfMeasure(Constants.instanceUnitOfMeasure)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationResponseDto instanceRealisation = RealisationResponseDtoMother.complete().build();

    public static final FruitCultivarResponseDto instanceFruitCultivar = FruitCultivarResponseDtoMother.complete().build();

    public static final HarvestedFruitClassResponseDto instanceHarvestedFruitClass = HarvestedFruitClassResponseDtoMother.complete().build();

    public static final BigDecimal instanceQuantity = BigDecimal.ONE;

    public static final UnitOfMeasureResponseDto instanceUnitOfMeasure = UnitOfMeasureResponseDtoMother.complete().build();

    public static final String instanceNote = "note";

  }

}
