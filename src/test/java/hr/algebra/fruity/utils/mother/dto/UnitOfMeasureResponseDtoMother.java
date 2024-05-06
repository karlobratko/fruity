package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitOfMeasureResponseDtoMother {

  public static UnitOfMeasureResponseDto.UnitOfMeasureResponseDtoBuilder complete() {
    return UnitOfMeasureResponseDto.builder()
      .id(Constants.instanceId)
      .abbreviation(Constants.instanceAbbreviation)
      .name(Constants.instanceName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final String instanceAbbreviation = "abbreviation";

  }

}