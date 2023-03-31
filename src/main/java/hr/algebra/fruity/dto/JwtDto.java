package hr.algebra.fruity.dto;

import java.util.Map;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JwtDto(
  Long userId,
  Long employeeId
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String userId = "userId";

    public static final String employeeId = "employeeId";

  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ClaimResolvers {

    public static final Function<Map<String, Object>, Long> userIdResolver = claims -> (Long) claims.get(Fields.userId);

    public static final Function<Map<String, Object>, Long> employeeIdResolver = claims -> (Long) claims.get(Fields.employeeId);

  }

}
