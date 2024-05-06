package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.AgentResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgentResponseDtoMother {

  public static AgentResponseDto.AgentResponseDtoBuilder complete() {
    return AgentResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .manufacturer(Constants.instanceManufacturer)
      .state(Constants.instanceState);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final String instanceManufacturer = "manufacturer";

    public static final String instanceState = "state";

  }

}