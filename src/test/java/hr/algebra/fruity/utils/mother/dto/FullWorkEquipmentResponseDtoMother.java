package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullWorkEquipmentResponseDtoMother {

  public static FullWorkEquipmentResponseDto.FullWorkEquipmentResponseDtoBuilder complete() {
    return FullWorkEquipmentResponseDto.builder()
      .work(Constants.instanceWork)
      .equipment(Constants.instanceEquipment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkResponseDto instanceWork = WorkResponseDtoMother.complete().build();

    public static final EquipmentResponseDto instanceEquipment = EquipmentResponseDtoMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}