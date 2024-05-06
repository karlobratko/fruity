package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.FruitSpecies;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FruitSpeciesMother {

  public static FruitSpecies.FruitSpeciesBuilder complete() {
    return FruitSpecies.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .name(Constants.instanceName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

  }

}