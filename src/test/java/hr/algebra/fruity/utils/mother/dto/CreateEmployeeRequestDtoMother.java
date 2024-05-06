package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateEmployeeRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateEmployeeRequestDtoMother {

  public static CreateEmployeeRequestDto.CreateEmployeeRequestDtoBuilder complete() {
    return CreateEmployeeRequestDto.builder()
      .firstName(Constants.instanceFirstName)
      .lastName(Constants.instanceLastName)
      .email(Constants.instanceEmail)
      .phoneNumber(Constants.instancePhoneNumber)
      .costPerHour(Constants.instanceCostPerHour);
  }

  public static class Constants {

    public static final String instanceFirstName = "firstName";

    public static final String instanceLastName = "lastName";

    public static final String instanceEmail = "employee@email.com";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

  }

}
