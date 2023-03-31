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
      .name(Constants.instanceDisplayName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final String instanceDisplayName = "displayName";

  }

}