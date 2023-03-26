package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public record EmployeeResponseDto(
  Long id,
  UUID uuid,
  Long userFk,
  String firstName,
  String lastName,
  String username,
  String email,
  String phoneNumber,
  BigDecimal costPerHour,
  Integer employeeRoleFk,
  Long registrationTokenFk
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String userFk = "userFk";

    public static final String firstName = "firstName";

    public static final String lastName = "lastName";

    public static final String username = "username";

    public static final String email = "email";

    public static final String phoneNumber = "phoneNumber";

    public static final String costPerHour = "costPerHour";

    public static final String employeeRoleFk = "employeeRoleFk";

    public static final String registrationTokenFk = "registrationTokenFk";

  }

}
