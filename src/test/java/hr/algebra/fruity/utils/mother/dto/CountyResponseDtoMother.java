package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountyResponseDtoMother {

  public static CountyResponseDto completeAndBuilt() {
    return new CountyResponseDto(
      Constants.instanceId,
      Constants.instanceUuid,
      Constants.instanceName,
      Constants.displayName
    );
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String displayName = "displayName";

  }

}