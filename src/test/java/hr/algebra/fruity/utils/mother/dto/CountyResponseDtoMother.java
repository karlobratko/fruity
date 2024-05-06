package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountyResponseDtoMother {

  public static CountyResponseDto.CountyResponseDtoBuilder complete() {
    return CountyResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .abbreviation(Constants.instanceAbbreviation);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final String instanceAbbreviation = "abbreviation";

  }

}