package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullRealisationAttachmentResponseDto(
  RealisationResponseDto realisation,
  AttachmentResponseDto attachment,
  BigDecimal costPerHour,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String realisation = "realisation";

    public static final String attachment = "attachment";

    public static final String costPerHour = "costPerHour";

    public static final String note = "note";

  }

}
