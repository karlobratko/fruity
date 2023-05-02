package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.HarvestedFruitClassResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HarvestedFruitClassResponseDtoMother {

  public static HarvestedFruitClassResponseDto.HarvestedFruitClassResponseDtoBuilder complete() {
    return HarvestedFruitClassResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .number(Constants.instanceNumber);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final Integer instanceNumber = 1;

  }

}