package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

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
}
