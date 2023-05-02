package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationRow;
import hr.algebra.fruity.model.Row;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealisationRowMother {

  public static RealisationRow.RealisationRowBuilder complete() {
    return RealisationRow.builder()
      .id(Constants.instanceId)
      .realisation(Constants.instanceRealisation)
      .row(Constants.instanceRow)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationRow.RealisationRowId instanceId = new RealisationRow.RealisationRowId(1L, 1L);

    public static final Realisation instanceRealisation = RealisationMother.complete().build();

    public static final Row instanceRow = RowMother.complete().build();

    public static final String instanceNote = "note";

  }

}
