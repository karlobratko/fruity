package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CadastralParcelOwnershipStatusMother {

  public static CadastralParcelOwnershipStatus.CadastralParcelOwnershipStatusBuilder complete() {
    return CadastralParcelOwnershipStatus.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .displayName(Constants.instanceDisplayName)
      .name(Constants.instanceName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceDisplayName = "displayName";

    public static final String instanceName = "name";

  }

}
