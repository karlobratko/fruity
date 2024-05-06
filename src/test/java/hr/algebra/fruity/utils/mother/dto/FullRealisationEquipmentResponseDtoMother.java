package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.dto.response.FullRealisationEquipmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRealisationEquipmentResponseDtoMother {

  public static FullRealisationEquipmentResponseDto.FullRealisationEquipmentResponseDtoBuilder complete() {
    return FullRealisationEquipmentResponseDto.builder()
      .realisation(Constants.instanceRealisation)
      .equipment(Constants.instanceEquipment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationResponseDto instanceRealisation = RealisationResponseDtoMother.complete().build();

    public static final EquipmentResponseDto instanceEquipment = EquipmentResponseDtoMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}