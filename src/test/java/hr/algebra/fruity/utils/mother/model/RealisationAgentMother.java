package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.model.RealisationAgent;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RealisationAgentMother {

  public static RealisationAgent.RealisationAgentBuilder complete() {
    return RealisationAgent.builder()
      .id(Constants.instanceId)
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

    public static final RealisationAgent.RealisationAgentId instanceId = new RealisationAgent.RealisationAgentId(1L, 1);

    public static final Realisation instanceRealisation = RealisationMother.complete().build();

    public static final Agent instanceAgent = AgentMother.complete().build();

    public static final BigDecimal instanceAgentQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceAgentUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final BigDecimal instanceCostPerUnitOfMeasure = BigDecimal.ONE;

    public static final BigDecimal instanceWaterQuantity = BigDecimal.ONE;

    public static final UnitOfMeasure instanceWaterUnitOfMeasure = UnitOfMeasureMother.complete().build();

    public static final String instanceNote = "note";

  }

}
