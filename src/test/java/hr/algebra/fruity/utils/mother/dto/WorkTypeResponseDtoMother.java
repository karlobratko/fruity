package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkTypeResponseDtoMother {

  public static WorkTypeResponseDto.WorkTypeResponseDtoBuilder complete() {
    return WorkTypeResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

  }

}
