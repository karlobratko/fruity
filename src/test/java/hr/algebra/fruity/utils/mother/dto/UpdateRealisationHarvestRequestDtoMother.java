package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRealisationHarvestRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRealisationHarvestRequestDtoMother {

  public static UpdateRealisationHarvestRequestDto.UpdateRealisationHarvestRequestDtoBuilder complete() {
    return UpdateRealisationHarvestRequestDto.builder()
      .quantity(Constants.instanceQuantity)
      .unitOfMeasureFk(Constants.instanceUnitOfMeasureFk)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final BigDecimal instanceQuantity = BigDecimal.ONE;

    public static final Integer instanceUnitOfMeasureFk = 1;

    public static final String instanceNote = "note";

  }

}
