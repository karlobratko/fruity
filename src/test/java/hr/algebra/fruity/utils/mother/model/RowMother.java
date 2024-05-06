package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.model.User;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowMother {

  public static Row.RowBuilder complete() {
    return Row.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .user(Constants.instanceUser)
      .rowCluster(Constants.instanceRowCluster)
      .ordinal(Constants.instanceOrdinal)
      .plantingYear(Constants.instancePlantingYear)
      .numberOfSeedlings(Constants.instanceNumberOfSeedlings)
      .fruitCultivar(Constants.instanceFruitCultivar);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final User instanceUser = UserMother.complete().build();

    public static final Integer instanceOrdinal = 1;

    public static final RowCluster instanceRowCluster = RowClusterMother.complete().build();

    public static final Integer instancePlantingYear = 1990;

    public static final Integer instanceNumberOfSeedlings = 1;

    public static final FruitCultivar instanceFruitCultivar = FruitCultivarMother.complete().build();

  }

}
