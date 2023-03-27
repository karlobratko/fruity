package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullEmployeeResponseDto(
  Long id,
  UUID uuid,
  UserResponseDto user,
  String firstName,
  String lastName,
  String username,
  String email,
  String phoneNumber,
  BigDecimal costPerHour,
  EmployeeRoleResponseDto employeeRole,
  RegistrationTokenResponseDto registrationToken
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String user = "user";

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String username = "username";

    public static final String email = "email";

    public static final String phoneNumber = "phoneNumber";

    public static final String costPerHour = "costPerHour";

    public static final String employeeRole = "employeeRole";

    public static final String registrationToken = "registrationToken";

  }

}
