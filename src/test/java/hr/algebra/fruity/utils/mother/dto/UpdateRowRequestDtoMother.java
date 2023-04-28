package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRowRequestDtoMother {

  public static UpdateRowRequestDto.UpdateRowRequestDtoBuilder complete() {
    return UpdateRowRequestDto.builder()
      .ordinal(Constants.instanceOrdinal)
      .plantingYear(Constants.instancePlantingYear)
      .numberOfSeedlings(Constants.instanceNumberOfSeedlings)
      .fruitCultivarFk(Constants.instanceFruitCultivarFk);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceOrdinal = 1;

    public static final Integer instancePlantingYear = 1990;

    public static final Integer instanceNumberOfSeedlings = 1;

    public static final Integer instanceFruitCultivarFk = 1;

  }

}