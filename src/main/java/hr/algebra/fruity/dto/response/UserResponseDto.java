package hr.algebra.fruity.dto.response;

import java.util.UUID;

public record UserResponseDto(
  Long id,
  UUID uuid,
  String name,
  String oib,
  String phoneNumber,
  String address,
  Integer countyFk
) {
}
