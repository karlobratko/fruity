package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.County;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountyMother {

  public static County.CountyBuilder complete() {
    return County.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .abbreviation(Constants.instanceAbbreviation)
      .name(Constants.instanceName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceAbbreviation = "abbreviation";

    public static final String instanceName = "name";

  }

}
