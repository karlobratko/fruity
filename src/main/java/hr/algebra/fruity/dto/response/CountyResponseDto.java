package hr.algebra.fruity.dto.response;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record CountyResponseDto(
  Integer id,
  UUID uuid,
  String abbreviation,
  String name
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String abbreviation = "abbreviation";

    public static final String name = "name";

  }

}
