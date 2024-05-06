package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkAgent;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkAgentMother {

  public static WorkAgent.WorkAgentBuilder complete() {
    return WorkAgent.builder()
      .id(Constants.instanceId)
      .work(Constants.instanceWork)
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

    public static final WorkAgent.WorkAgentId instanceId = new WorkAgent.WorkAgentId(1L, 1);

    public static final Work instanceWork = WorkMother.complete().build();

    public static final Agent instanceAgent = AgentMother.complete().build();

    public static final BigDecimal instanceAgentQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceAgentUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final BigDecimal instanceCostPerUnitOfMeasure = BigDecimal.ONE;

    public static final BigDecimal instanceWaterQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceWaterUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final String instanceNote = "note";

  }

}
