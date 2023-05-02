package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record HarvestedFruitClassResponseDto(
  Integer id,
  Integer number,
  String name
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String number = "number";

    public static final String name = "name";

  }

}
