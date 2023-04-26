package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullWorkAttachmentResponseDtoMother {

  public static FullWorkAttachmentResponseDto.FullWorkAttachmentResponseDtoBuilder complete() {
    return FullWorkAttachmentResponseDto.builder()
      .work(Constants.instanceWork)
      .attachment(Constants.instanceAttachment)
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final WorkResponseDto instanceWork = WorkResponseDtoMother.complete().build();

    public static final AttachmentResponseDto instanceAttachment = AttachmentResponseDtoMother.complete().build();

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}