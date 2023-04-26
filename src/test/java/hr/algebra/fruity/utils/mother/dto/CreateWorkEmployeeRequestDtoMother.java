package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateWorkEmployeeRequestDtoMother {

  public static CreateWorkEmployeeRequestDto.CreateWorkEmployeeRequestDtoBuilder complete() {
    return CreateWorkEmployeeRequestDto.builder()
      .employeeFk(Constants.instanceEmployeeFk)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceEmployeeFk = 1L;

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
