package hr.algebra.fruity.dto.response;

import java.util.UUID;

public record EmployeeRoleResponseDto(
  Integer id,
  UUID uuid,
  String name,
  String displayName
) {
}
