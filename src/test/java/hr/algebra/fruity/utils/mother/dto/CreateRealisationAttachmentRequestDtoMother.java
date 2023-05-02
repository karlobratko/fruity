package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.CreateRealisationAttachmentRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRealisationAttachmentRequestDtoMother {

  public static CreateRealisationAttachmentRequestDto.CreateRealisationAttachmentRequestDtoBuilder complete() {
    return CreateRealisationAttachmentRequestDto.builder()
      .attachmentFk(Constants.instanceAttachmentFk)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceAttachmentFk = 1L;

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
