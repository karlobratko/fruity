package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDto;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.utils.mother.model.AttachmentMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateRealisationAttachmentRequestDtoMother {

  public static JoinedCreateRealisationAttachmentRequestDto.JoinedCreateRealisationAttachmentRequestDtoBuilder complete() {
    return JoinedCreateRealisationAttachmentRequestDto.builder()
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
