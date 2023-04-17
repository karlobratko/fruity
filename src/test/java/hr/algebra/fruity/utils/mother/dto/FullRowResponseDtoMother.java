package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRowResponseDtoMother {

  public static FullRowResponseDto.FullRowResponseDtoBuilder complete() {
    return FullRowResponseDto.builder()
      .id(Constants.instanceId)
      .rowCluster(Constants.instanceRowCluster)
      .ordinal(Constants.instanceOrdinal)
      .plantingYear(Constants.instancePlantingYear)
      .numberOfSeedlings(Constants.instanceNumberOfSeedlings)
      .fruitCultivar(Constants.instanceFruitCultivar);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final Integer instanceOrdinal = 1;

    public static final RowClusterResponseDto instanceRowCluster = RowClusterResponseDtoMother.complete().build();

    public static final Integer instancePlantingYear = 1990;

    public static final Integer instanceNumberOfSeedlings = 1;

    public static final FruitCultivarResponseDto instanceFruitCultivar = FruitCultivarResponseDtoMother.complete().build();

  }

}
