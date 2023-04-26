package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateWorkEquipmentRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateWorkEquipmentRequestDtoMother {

  public static UpdateWorkEquipmentRequestDto.UpdateWorkEquipmentRequestDtoBuilder complete() {
    return UpdateWorkEquipmentRequestDto.builder()
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceEquipmentFk = 1L;

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
