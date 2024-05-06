package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateRealisationAgentRequestDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRealisationAgentRequestDtoMother {

  public static UpdateRealisationAgentRequestDto.UpdateRealisationAgentRequestDtoBuilder complete() {
    return UpdateRealisationAgentRequestDto.builder()
      .agentQuantity(Constants.instanceAgentQuantity)
      .agentUnitOfMeasureFk(Constants.instanceAgentUnitOfMeasureFk)
      .costPerUnitOfMeasure(Constants.instanceCostPerUnitOfMeasure)
      .waterQuantity(Constants.instanceWaterQuantity)
      .waterUnitOfMeasureFk(Constants.instanceWaterUnitOfMeasureFk)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final BigDecimal instanceAgentQuantity = BigDecimal.ONE;

    public static final Integer instanceAgentUnitOfMeasureFk = 1;

    public static final BigDecimal instanceCostPerUnitOfMeasure = BigDecimal.ONE;

    public static final BigDecimal instanceWaterQuantity = BigDecimal.ONE;

    public static final Integer instanceWaterUnitOfMeasureFk = 1;

    public static final String instanceNote = "note";

  }

}
