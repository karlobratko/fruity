package hr.algebra.fruity.dto.response;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record EmployeeRoleResponseDto(
  Integer id,
  UUID uuid,
  String name,
  String displayName
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String name = "name";

    public static final String displayName = "displayName";

  }

}
