package hr.algebra.fruity.model.codebook;

public enum AgentStates {

  HARD(1, "Kruto"),
  LIQUID(2, "Tekuće"),
  GRANULAR(3, "Granulirano"),
  CRYSTAL_GRANULES(4, "Kristalne granule"),
  UNKNOWN(5, "Nepoznato");

  private final Integer id;

  private final String displayName;

  AgentStates(Integer id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public Integer id() {
    return id;
  }

  public String displayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return this.displayName;
  }

}
