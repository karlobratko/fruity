package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkEquipment;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkEquipmentMother {

  public static WorkEquipment.WorkEquipmentBuilder complete() {
    return WorkEquipment.builder()
      .id(Constants.instanceId)
      .work(Constants.instanceWork)
      .equipment(Constants.instanceEquipment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkEquipment.WorkEquipmentId instanceId = new WorkEquipment.WorkEquipmentId(1L, 1L);

    public static final Work instanceWork = WorkMother.complete().build();

    public static final Equipment instanceEquipment = EquipmentMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
