package hr.algebra.fruity.model.codebook;

public enum AgentStates {

  HARD(1, "kruto"),
  LIQUID(2, "tekuće"),
  GRANULAR(3, "granulirano"),
  CRYSTAL_GRANULES(4, "kristalne granule"),
  UNKNOWN(5, "nepoznato");

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
