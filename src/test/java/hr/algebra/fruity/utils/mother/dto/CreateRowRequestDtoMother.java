package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRowRequestDtoMother {

  public static CreateRowRequestDto.CreateRowRequestDtoBuilder complete() {
    return CreateRowRequestDto.builder()
      .rowClusterFk(Constants.instanceRowClusterFk)
      .ordinal(Constants.instanceOrdinal)
      .plantingYear(Constants.instancePlantingYear)
      .numberOfSeedlings(Constants.instanceNumberOfSeedlings)
      .fruitCultivarFk(Constants.instanceFruitCultivarFk);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceOrdinal = 1;

    public static final Long instanceRowClusterFk = 1L;

    public static final Integer instancePlantingYear = 1990;

    public static final Integer instanceNumberOfSeedlings = 1;

    public static final Integer instanceFruitCultivarFk = 1;

  }

}
