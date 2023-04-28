package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.Employee;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedCreateWorkEmployeeRequestDto(
  Employee employee,
  BigDecimal costPerHour,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String employee = "employee";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

}
