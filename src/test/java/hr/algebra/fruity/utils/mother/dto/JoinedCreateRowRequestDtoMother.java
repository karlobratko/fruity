package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRowRequestDto;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.utils.mother.model.FruitCultivarMother;
import hr.algebra.fruity.utils.mother.model.RowClusterMother;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateRowRequestDtoMother {

  public static JoinedCreateRowRequestDto.JoinedCreateRowRequestDtoBuilder complete() {
    return JoinedCreateRowRequestDto.builder()
      .rowCluster(Constants.instanceRowCluster)
      .ordinal(Constants.instanceOrdinal)
      .plantingYear(Constants.instancePlantingYear)
      .numberOfSeedlings(Constants.instanceNumberOfSeedlings)
      .fruitCultivar(Constants.instanceFruitCultivar);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceOrdinal = 1;

    public static final RowCluster instanceRowCluster = RowClusterMother.complete().build();

    public static final Integer instancePlantingYear = 1990;

    public static final Integer instanceNumberOfSeedlings = 1;

    public static final FruitCultivar instanceFruitCultivar = FruitCultivarMother.complete().build();

  }

}
