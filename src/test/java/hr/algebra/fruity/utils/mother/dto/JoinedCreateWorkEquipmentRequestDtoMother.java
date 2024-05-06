package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDto;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.utils.mother.model.EquipmentMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateWorkEquipmentRequestDtoMother {

  public static JoinedCreateWorkEquipmentRequestDto.JoinedCreateWorkEquipmentRequestDtoBuilder complete() {
    return JoinedCreateWorkEquipmentRequestDto.builder()
      .equipment(Constants.instanceEquipment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Equipment instanceEquipment = EquipmentMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
