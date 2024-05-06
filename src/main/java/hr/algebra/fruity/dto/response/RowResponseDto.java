package hr.algebra.fruity.dto.response;

import hr.algebra.fruity.repository.FruitCultivarRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RowResponseDto(
  Long id,
  Integer ordinal,
  Long rowClusterFk,
  Integer numberOfSeedlings,
  FruitCultivarResponseDto fruitCultivar,
  Integer plantingYear
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String ordinal = "ordinal";

    public static final String rowClusterFk = "rowClusterFk";

    public static final String numberOfSeedlings = "numberOfSeedlings";

    public static final String fruitCultivar = "fruitCultivar";

    public static final String plantingYear = "plantingYear";

  }

}