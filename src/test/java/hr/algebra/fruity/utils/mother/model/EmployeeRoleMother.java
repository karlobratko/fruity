package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.EmployeeRole;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRoleMother {

  public static EmployeeRole.EmployeeRoleBuilder complete() {
    return EmployeeRole.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .name(Constants.instanceName)
      .displayName(Constants.displayName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String displayName = "displayName";

  }

}
