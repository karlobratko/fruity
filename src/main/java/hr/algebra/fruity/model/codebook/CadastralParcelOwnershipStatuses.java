package hr.algebra.fruity.model.codebook;

public enum CadastralParcelOwnershipStatuses {

  OWNED(1, "u vlasništvu"),
  LEASED(2, "u zakupu");

  private final Integer id;

  private final String displayName;

  CadastralParcelOwnershipStatuses(Integer id, String displayName) {
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
