package hr.algebra.fruity.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullCadastralMunicipalityResponseDto(
  Integer id,
  Integer registrationNumber,
  String name,
  String department,
  String regionalCadastreOffice
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String registrationNumber = "registrationNumber";

    public static final String name = "name";

    public static final String department = "department";

    public static final String regionalCadastreOffice = "regionalCadastreOffice";

  }

}
