package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.dto.response.FullWorkEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullWorkEmployeeResponseDtoMother {

  public static FullWorkEmployeeResponseDto.FullWorkEmployeeResponseDtoBuilder complete() {
    return FullWorkEmployeeResponseDto.builder()
      .work(Constants.instanceWork)
      .employee(Constants.instanceEmployee)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkResponseDto instanceWork = WorkResponseDtoMother.complete().build();

    public static final EmployeeResponseDto instanceEmployee = EmployeeResponseDtoMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}