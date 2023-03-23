package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

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
}
