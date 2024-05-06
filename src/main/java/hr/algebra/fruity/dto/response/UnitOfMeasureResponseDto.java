package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record UnitOfMeasureResponseDto(
  Integer id,
  String abbreviation,
  String name
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String abbreviation = "abbreviation";

    public static final String name = "name";

  }

}
