package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkAttachment;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkAttachmentMother {

  public static WorkAttachment.WorkAttachmentBuilder complete() {
    return WorkAttachment.builder()
      .id(Constants.instanceId)
      .work(Constants.instanceWork)
      .attachment(Constants.instanceAttachment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkAttachment.WorkAttachmentId instanceId = new WorkAttachment.WorkAttachmentId(1L, 1L);

    public static final Work instanceWork = WorkMother.complete().build();

    public static final Attachment instanceAttachment = AttachmentMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
