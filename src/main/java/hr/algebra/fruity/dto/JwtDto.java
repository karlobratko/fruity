package hr.algebra.fruity.dto;

import java.util.List;

public record JwtDto(
  Long userId,
  Long employeeId,
  String username,
  List<String> roles
) {
}
