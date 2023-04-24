package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record WorkTypeResponseDto(
  Integer id,
  String name,
  Boolean workersTab,
  Boolean rowsTab,
  Boolean equipmentsTab,
  Boolean attachmentsTab,
  Boolean agentsTab,
  Boolean quantitiesTab
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String name = "name";

    public static final String workersTab = "workersTab";

    public static final String rowsTab = "rowsTab";

    public static final String equipmentsTab = "equipmentsTab";

    public static final String attachmentsTab = "attachmentsTab";

    public static final String agentsTab = "agentsTab";

    public static final String quantitiesTab = "quantitiesTab";

  }

}
