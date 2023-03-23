package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeRoleResponseDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRoleResponseDtoMother {

  public static EmployeeRoleResponseDto completeAndBuilt() {
    return new EmployeeRoleResponseDto(
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
