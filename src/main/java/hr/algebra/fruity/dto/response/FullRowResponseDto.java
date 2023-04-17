package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullRowResponseDto(
  Long id,
  Integer ordinal,
  RowClusterResponseDto rowCluster,
  Integer numberOfSeedlings,
  FruitCultivarResponseDto fruitCultivar,
  Integer plantingYear
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String ordinal = "ordinal";

    public static final String rowCluster = "rowCluster";

    public static final String numberOfSeedlings = "numberOfSeedlings";

    public static final String fruitCultivar = "fruitCultivar";

    public static final String plantingYear = "plantingYear";

  }

}