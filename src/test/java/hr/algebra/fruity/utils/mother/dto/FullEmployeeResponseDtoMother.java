package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeRoleResponseDto;
import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullEmployeeResponseDtoMother {

  public static FullEmployeeResponseDto.FullEmployeeResponseDtoBuilder complete() {
    return FullEmployeeResponseDto.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .user(Constants.instanceUser)
      .firstName(Constants.instanceFirstName)
      .lastName(Constants.instanceLastName)
      .username(Constants.instanceUsername)
      .email(Constants.instanceEmail)
      .phoneNumber(Constants.instancePhoneNumber)
      .costPerHour(Constants.instanceCostPerHour)
      .employeeRole(Constants.instanceEmployeeRole)
      .registrationToken(Constants.instanceRegistrationToken);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final UserResponseDto instanceUser = UserResponseDtoMother.complete().build();

    public static final String instanceFirstName = "firstName";

    public static final String instanceLastName = "lastName";

    public static final String instanceUsername = "username";

    public static final String instanceEmail = "employee@email.com";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final EmployeeRoleResponseDto instanceEmployeeRole = EmployeeRoleResponseDtoMother.complete().build();

    public static final RegistrationTokenResponseDto instanceRegistrationToken = RegistrationTokenResponseDtoMother.complete().build();

  }

}
