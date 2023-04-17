package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FruitCultivarResponseDtoMother {

  public static FruitCultivarResponseDto.FruitCultivarResponseDtoBuilder complete() {
    return FruitCultivarResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .species(Constants.instanceSpecies);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final String instanceSpecies = "species";

  }

}