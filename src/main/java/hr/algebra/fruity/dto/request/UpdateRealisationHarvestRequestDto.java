package hr.algebra.fruity.dto.request;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record UpdateRealisationHarvestRequestDto(
  BigDecimal quantity,
  Integer unitOfMeasureFk,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String quantity = "quantity";

    public static final String unitOfMeasureFk = "unitOfMeasureFk";

    public static final String note = "note";

  }

}

