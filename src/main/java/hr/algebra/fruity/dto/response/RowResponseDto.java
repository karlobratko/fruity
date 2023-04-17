package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record RowResponseDto(
  Long id,
  Integer ordinal,
  Long rowClusterFk,
  Integer numberOfSeedlings,
  Integer fruitCultivarFk,
  Integer plantingYear
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String ordinal = "ordinal";

    public static final String rowClusterFk = "rowClusterFk";

    public static final String numberOfSeedlings = "numberOfSeedlings";

    public static final String fruitCultivarFk = "fruitCultivarFk";

    public static final String plantingYear = "plantingYear";

  }

}