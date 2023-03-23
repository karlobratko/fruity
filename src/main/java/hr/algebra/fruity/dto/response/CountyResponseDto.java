package hr.algebra.fruity.dto.response;

import java.util.UUID;

public record CountyResponseDto(
  Integer id,
  UUID uuid,
  String abbreviation,
  String name
) {
}
