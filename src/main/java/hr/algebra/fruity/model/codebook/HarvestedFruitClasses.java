package hr.algebra.fruity.model.codebook;

public enum HarvestedFruitClasses {

  I(1, 1, "I. klasa"),
  II(2, 2, "II. klasa"),
  III(3, 3, "III. klasa"),
  IV(4, 4, "IV. klasa"),
  V(5, 5, "V. klasa"),
  VI(6, 6, "VI. klasa");

  private final Integer id;

  private final Integer number;

  private final String displayName;

  HarvestedFruitClasses(Integer id, Integer number, String displayName) {
    this.id = id;
    this.number = number;
    this.displayName = displayName;
  }

  public Integer id() {
    return id;
  }

  public Integer number() {
    return number;
  }

  public String displayName() {
    return displayName;
  }

}
