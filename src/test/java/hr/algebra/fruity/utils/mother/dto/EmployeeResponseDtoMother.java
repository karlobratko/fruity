package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeResponseDtoMother {

  public static EmployeeResponseDto.EmployeeResponseDtoBuilder complete() {
    return EmployeeResponseDto.builder()
      .id(Constants.instanceId)
      .firstName(Constants.instanceFirstName)
      .lastName(Constants.instanceLastName)
      .email(Constants.instanceEmail)
      .phoneNumber(Constants.instancePhoneNumber)
      .role(Constants.instanceRole)
      .costPerHour(Constants.instanceCostPerHour);
  }

  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceFirstName = "firstName";

    public static final String instanceLastName = "lastName";

    public static final String instanceEmail = "email";

    public static final String instancePhoneNumber = "phoneNumber";

    public static final String instanceRole = "role";

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

  }

}