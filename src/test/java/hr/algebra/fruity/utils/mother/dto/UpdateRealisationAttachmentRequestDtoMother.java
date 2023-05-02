package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRealisationAttachmentRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRealisationAttachmentRequestDtoMother {

  public static UpdateRealisationAttachmentRequestDto.UpdateRealisationAttachmentRequestDtoBuilder complete() {
    return UpdateRealisationAttachmentRequestDto.builder()
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
