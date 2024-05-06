package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAgentRequestDto;
import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.utils.mother.model.AgentMother;
import hr.algebra.fruity.utils.mother.model.UnitOfMeasureMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateWorkAgentRequestDtoMother {

  public static JoinedCreateWorkAgentRequestDto.JoinedCreateWorkAgentRequestDtoBuilder complete() {
    return JoinedCreateWorkAgentRequestDto.builder()
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

    public static final Agent instanceAgent = AgentMother.complete().build();

    public static final BigDecimal instanceAgentQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceAgentUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final BigDecimal instanceCostPerUnitOfMeasure = BigDecimal.ONE;

    public static final BigDecimal instanceWaterQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceWaterUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final String instanceNote = "note";

  }

}
