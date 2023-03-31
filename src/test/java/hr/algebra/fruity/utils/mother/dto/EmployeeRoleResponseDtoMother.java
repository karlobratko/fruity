package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeRoleResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRoleResponseDtoMother {

  public static EmployeeRoleResponseDto.EmployeeRoleResponseDtoBuilder complete() {
    return EmployeeRoleResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

  }

}
