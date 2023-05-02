package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.HarvestedFruitClass;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HarvestedFruitClassMother {

  public static HarvestedFruitClass.HarvestedFruitClassBuilder complete() {
    return HarvestedFruitClass.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .name(Constants.instanceName)
      .displayName(Constants.instanceDisplayName)
      .number(Constants.instanceNumber);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String instanceDisplayName = "displayName";

    public static final Integer instanceNumber = 1;

  }

}