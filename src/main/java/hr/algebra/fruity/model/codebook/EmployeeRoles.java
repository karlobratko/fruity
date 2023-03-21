package hr.algebra.fruity.model.codebook;

public enum EmployeeRoles {

  ROLE_MANAGER(1, "Voditelj"),
  ROLE_PERFORMER(2, "Izvršitelj");

  private final Integer id;

  private final String displayName;

  EmployeeRoles(Integer id, String displayName) {
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
