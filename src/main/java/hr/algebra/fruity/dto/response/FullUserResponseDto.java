package hr.algebra.fruity.dto.response;

import java.util.UUID;

public record FullUserResponseDto(
  Long id,
  UUID uuid,
  String name,
  String oib,
  String phoneNumber,
  String address,
  CountyResponseDto county
) {
}
