package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateWorkAttachmentRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateWorkAttachmentRequestDtoMother {

  public static UpdateWorkAttachmentRequestDto.UpdateWorkAttachmentRequestDtoBuilder complete() {
    return UpdateWorkAttachmentRequestDto.builder()
      .costPerHour(Constants.instanceCostPerHour)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final BigDecimal instanceCostPerHour = BigDecimal.ONE;

    public static final String instanceNote = "note";

  }

}
