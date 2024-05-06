package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record WorkEmployeeResponseDto(
  Long workFk,
  EmployeeResponseDto employee,
  BigDecimal costPerHour,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String workFk = "workFk";

    public static final String employee = "employee";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

}