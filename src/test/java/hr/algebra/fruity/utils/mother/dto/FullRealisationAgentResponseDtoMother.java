package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.dto.response.FullRealisationAgentResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullRealisationAgentResponseDtoMother {

  public static FullRealisationAgentResponseDto.FullRealisationAgentResponseDtoBuilder complete() {
    return FullRealisationAgentResponseDto.builder()
      .realisation(Constants.instanceRealisation)
      .agent(Constants.instanceAgent)
      .agentQuantity(Constants.instanceAgentQuantity)
      .agentUnitOfMeasure(Constants.instanceAgentUnitOfMeasure)
      .costPerUnitOfMeasure(Constants.instanceCostPerUnitOfMeasure)
      .waterQuantity(Constants.instanceWaterQuantity)
      .waterUnitOfMeasure(Constants.instanceWaterUnitOfMeasure)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final RealisationResponseDto instanceRealisation = RealisationResponseDtoMother.complete().build();

    public static final AgentResponseDto instanceAgent = AgentResponseDtoMother.complete().build();

    public static final BigDecimal instanceAgentQuantity = BigDecimal.ONE;

    public static final UnitOfMeasureResponseDto instanceAgentUnitOfMeasure = UnitOfMeasureResponseDtoMother.complete().build();

    public static final BigDecimal instanceCostPerUnitOfMeasure = BigDecimal.ONE;

    public static final BigDecimal instanceWaterQuantity = BigDecimal.ONE;

    public static final UnitOfMeasureResponseDto instanceWaterUnitOfMeasure = UnitOfMeasureResponseDtoMother.complete().build();

    public static final String instanceNote = "note";

  }

}
