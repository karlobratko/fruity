package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record AgentResponseDto(
  Integer id,
  String name,
  String manufacturer,
  String state
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String name = "name";

    public static final String manufacturer = "manufacturer";

    public static final String state = "state";

  }

}
