package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDto;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.utils.mother.model.AttachmentMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateWorkAttachmentRequestDtoMother {

  public static JoinedCreateWorkAttachmentRequestDto.JoinedCreateWorkAttachmentRequestDtoBuilder complete() {
    return JoinedCreateWorkAttachmentRequestDto.builder()
      .attachment(Constants.instanceAttachment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Attachment instanceAttachment = AttachmentMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
