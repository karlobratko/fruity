package hr.algebra.fruity.model.codebook;

public enum WorkTypes {

  MOWING(1, "Košnja", true, true, true, true, false, false),
  PLANTING(2, "Sadnja", true, true, true, true, false, false),
  PRUNING(3, "Rezidba", true, true, true, true, false, false),
  IRRIGATION(4, "Navodnjavanje", true, true, true, true, false, false),
  DRESSING(5, "Prihrana", true, true, true, true, true, false),
  PROTECTION(6, "Zaštita", true, true, true, true, true, false),
  HARVEST(7, "Berba", true, true, true, true, false, true);

  private final Integer id;

  private final String displayName;

  private final Boolean workersTab;

  private final Boolean rowsTab;

  private final Boolean equipmentsTab;

  private final Boolean attachmentsTab;

  private final Boolean agentsTab;

  private final Boolean quantitiesTab;


  WorkTypes(Integer id, String displayName, Boolean workersTab, Boolean rowsTab, Boolean equipmentsTab, Boolean attachmentsTab, Boolean agentsTab, Boolean quantitiesTab) {
    this.id = id;
    this.displayName = displayName;
    this.workersTab = workersTab;
    this.rowsTab = rowsTab;
    this.equipmentsTab = equipmentsTab;
    this.attachmentsTab = attachmentsTab;
    this.agentsTab = agentsTab;
    this.quantitiesTab = quantitiesTab;
  }

  public Integer id() {
    return id;
  }

  public String displayName() {
    return displayName;
  }

  public Boolean workersTab() {
    return workersTab;
  }

  public Boolean rowsTab() {
    return rowsTab;
  }

  public Boolean equipmentsTab() {
    return equipmentsTab;
  }

  public Boolean attachmentsTab() {
    return attachmentsTab;
  }

  public Boolean agentsTab() {
    return agentsTab;
  }

  public Boolean quantitiesTab() {
    return quantitiesTab;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
