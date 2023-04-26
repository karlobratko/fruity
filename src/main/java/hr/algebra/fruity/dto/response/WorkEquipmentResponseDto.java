package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record WorkEquipmentResponseDto(
  Long workFk,
  EquipmentResponseDto equipment,
  BigDecimal costPerHour,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String workFk = "workFk";

    public static final String equipment = "equipment";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

}