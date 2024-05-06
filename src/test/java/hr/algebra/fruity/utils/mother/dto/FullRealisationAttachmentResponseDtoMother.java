package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.dto.response.FullRealisationAttachmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRealisationAttachmentResponseDtoMother {

  public static FullRealisationAttachmentResponseDto.FullRealisationAttachmentResponseDtoBuilder complete() {
    return FullRealisationAttachmentResponseDto.builder()
      .realisation(Constants.instanceRealisation)
      .attachment(Constants.instanceAttachment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationResponseDto instanceRealisation = RealisationResponseDtoMother.complete().build();

    public static final AttachmentResponseDto instanceAttachment = AttachmentResponseDtoMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}