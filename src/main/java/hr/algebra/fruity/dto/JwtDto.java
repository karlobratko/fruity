package hr.algebra.fruity.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JwtDto(
  Long userId,
  Long employeeId,
  String username,
  List<String> roles
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String userId = "userId";

    public static final String employeeId = "employeeId";

    public static final String username = "username";

    public static final String roles = "roles";

  }

}
