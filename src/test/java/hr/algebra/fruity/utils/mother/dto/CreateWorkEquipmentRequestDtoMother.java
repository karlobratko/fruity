package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateWorkEquipmentRequestDtoMother {

  public static CreateWorkEquipmentRequestDto.CreateWorkEquipmentRequestDtoBuilder complete() {
    return CreateWorkEquipmentRequestDto.builder()
      .equipmentFk(Constants.instanceEquipmentFk)
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
