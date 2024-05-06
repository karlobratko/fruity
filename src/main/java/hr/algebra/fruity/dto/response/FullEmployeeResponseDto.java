package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullEmployeeResponseDto(
  Long id,
  String firstName,
  String lastName,
  String username,
  String email,
  String phoneNumber,
  BigDecimal costPerHour,
  String role,
  UUID mobileToken
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String username = "username";

    public static final String email = "email";

    public static final String phoneNumber = "phoneNumber";

    public static final String costPerHour = "costPerHour";

    public static final String role = "role";

    public static final String mobileToken = "mobileToken";

  }

}
