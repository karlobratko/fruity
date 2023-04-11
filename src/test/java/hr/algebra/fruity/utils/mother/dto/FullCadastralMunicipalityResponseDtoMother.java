package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullCadastralMunicipalityResponseDtoMother {

  public static FullCadastralMunicipalityResponseDto.FullCadastralMunicipalityResponseDtoBuilder complete() {
    return FullCadastralMunicipalityResponseDto.builder()
      .id(Constants.instanceId)
      .registrationNumber(Constants.instanceRegistrationNumber)
      .name(Constants.instanceName)
      .department(Constants.instanceDepartment)
      .regionalCadastreOffice(Constants.instanceRegionalCadastreOffice);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceId = 1;

    public static final Integer instanceRegistrationNumber = 1;

    public static final String instanceName = "name";

    public static final String instanceDepartment = "department";

    public static final String instanceRegionalCadastreOffice = "regionalCadastreOffice";

  }

}