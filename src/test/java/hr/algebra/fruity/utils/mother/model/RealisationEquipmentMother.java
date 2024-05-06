package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationEquipment;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealisationEquipmentMother {

  public static RealisationEquipment.RealisationEquipmentBuilder complete() {
    return RealisationEquipment.builder()
      .id(Constants.instanceId)
      .realisation(Constants.instanceRealisation)
      .equipment(Constants.instanceEquipment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationEquipment.RealisationEquipmentId instanceId = new RealisationEquipment.RealisationEquipmentId(1L, 1L);

    public static final Realisation instanceRealisation = RealisationMother.complete().build();

    public static final Equipment instanceEquipment = EquipmentMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
