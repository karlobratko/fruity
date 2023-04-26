package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkRowMother {

  public static WorkRow.WorkRowBuilder complete() {
    return WorkRow.builder()
      .id(Constants.instanceId)
      .work(Constants.instanceWork)
      .row(Constants.instanceRow)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkRow.WorkRowId instanceId = new WorkRow.WorkRowId(1L, 1L);

    public static final Work instanceWork = WorkMother.complete().build();

    public static final Row instanceRow = RowMother.complete().build();

    public static final String instanceNote = "note";

  }

}
