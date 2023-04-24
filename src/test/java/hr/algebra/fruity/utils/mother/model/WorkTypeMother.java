package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.WorkType;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkTypeMother {

  public static WorkType.WorkTypeBuilder complete() {
    return WorkType.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .name(Constants.instanceName)
      .displayName(Constants.instanceDisplayName)
      .workersTab(Constants.instanceWorkersTab)
      .rowsTab(Constants.instanceRowsTab)
      .equipmentsTab(Constants.instanceEquipmentsTab)
      .attachmentsTab(Constants.instanceAttachmentsTab)
      .agentsTab(Constants.instanceAgentsTab)
      .quantitiesTab(Constants.instanceQuantitiesTab);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String instanceDisplayName = "displayName";

    public static final Boolean instanceWorkersTab = false;

    public static final Boolean instanceRowsTab = false;

    public static final Boolean instanceEquipmentsTab = false;

    public static final Boolean instanceAttachmentsTab = false;

    public static final Boolean instanceAgentsTab = false;

    public static final Boolean instanceQuantitiesTab = false;

  }

}
