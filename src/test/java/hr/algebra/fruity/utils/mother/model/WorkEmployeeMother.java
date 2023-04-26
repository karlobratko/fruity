package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkEmployee;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkEmployeeMother {

  public static WorkEmployee.WorkEmployeeBuilder complete() {
    return WorkEmployee.builder()
      .id(Constants.instanceId)
      .work(Constants.instanceWork)
      .employee(Constants.instanceEmployee)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkEmployee.WorkEmployeeId instanceId = new WorkEmployee.WorkEmployeeId(1L, 1L);

    public static final Work instanceWork = WorkMother.complete().build();

    public static final Employee instanceEmployee = EmployeeMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
