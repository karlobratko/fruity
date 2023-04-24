package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkTypeResponseDtoMother {

  public static WorkTypeResponseDto.WorkTypeResponseDtoBuilder complete() {
    return WorkTypeResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
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
