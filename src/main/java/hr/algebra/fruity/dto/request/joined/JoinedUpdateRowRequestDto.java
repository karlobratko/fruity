package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.FruitCultivar;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedUpdateRowRequestDto(
  Integer ordinal,
  Integer numberOfSeedlings,
  FruitCultivar fruitCultivar,
  Integer plantingYear
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String ordinal = "ordinal";

    public static final String rowCluster = "rowCluster";

    public static final String numberOfSeedlings = "numberOfSeedlings";

    public static final String fruitCultivar = "fruitCultivar";

    public static final String plantingYear = "plantingYear";

  }

}