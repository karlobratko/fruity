package hr.algebra.fruity.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ClaimResolvers {

    public static final Function<Map<String, Object>, Long> userIdResolver = claims -> (Long) claims.get(Fields.userId);

    public static final Function<Map<String, Object>, Long> employeeIdResolver = claims -> (Long) claims.get(Fields.employeeId);

    public static final Function<Map<String, Object>, String> usernameResolver = claims -> (String) claims.get(Fields.username);

    @SuppressWarnings("unchecked")
    public static final Function<Map<String, Object>, List<String>> rolesResolver = claims -> new ArrayList<>((Collection<? extends String>) claims.get(Fields.roles));

  }

}
