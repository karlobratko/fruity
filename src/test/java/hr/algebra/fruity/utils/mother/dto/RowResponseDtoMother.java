package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowResponseDtoMother {

  public static RowResponseDto.RowResponseDtoBuilder complete() {
    return RowResponseDto.builder()
      .id(Constants.instanceId)
      .rowClusterFk(Constants.instanceRowClusterFk)
      .ordinal(Constants.instanceOrdinal)
      .plantingYear(Constants.instancePlantingYear)
      .numberOfSeedlings(Constants.instanceNumberOfSeedlings)
      .fruitCultivar(Constants.instanceFruitCultivar);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final Integer instanceOrdinal = 1;

    public static final Long instanceRowClusterFk = 1L;

    public static final Integer instancePlantingYear = 1990;

    public static final Integer instanceNumberOfSeedlings = 1;

    public static final FruitCultivarResponseDto instanceFruitCultivar = FruitCultivarResponseDtoMother.complete().build();

  }

}
