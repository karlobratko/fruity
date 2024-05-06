package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationAttachment;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealisationAttachmentMother {

  public static RealisationAttachment.RealisationAttachmentBuilder complete() {
    return RealisationAttachment.builder()
      .id(Constants.instanceId)
      .realisation(Constants.instanceRealisation)
      .attachment(Constants.instanceAttachment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationAttachment.RealisationAttachmentId instanceId = new RealisationAttachment.RealisationAttachmentId(1L, 1L);

    public static final Realisation instanceRealisation = RealisationMother.complete().build();

    public static final Attachment instanceAttachment = AttachmentMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
