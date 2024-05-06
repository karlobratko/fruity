package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CadastralMunicipalityResponseDtoMother {

  public static CadastralMunicipalityResponseDto.CadastralMunicipalityResponseDtoBuilder complete() {
    return CadastralMunicipalityResponseDto.builder()
      .id(Constants.instanceId)
      .registrationNumber(Constants.instanceRegistrationNumber)
      .name(Constants.instanceName);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceId = 1;

    public static final Integer instanceRegistrationNumber = 1;

    public static final String instanceName = "name";

  }

}