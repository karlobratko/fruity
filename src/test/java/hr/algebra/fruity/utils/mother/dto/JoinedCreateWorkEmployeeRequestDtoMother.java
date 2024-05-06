package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDto;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.utils.mother.model.EmployeeMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateWorkEmployeeRequestDtoMother {

  public static JoinedCreateWorkEmployeeRequestDto.JoinedCreateWorkEmployeeRequestDtoBuilder complete() {
    return JoinedCreateWorkEmployeeRequestDto.builder()
      .employee(Constants.instanceEmployee)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Employee instanceEmployee = EmployeeMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}