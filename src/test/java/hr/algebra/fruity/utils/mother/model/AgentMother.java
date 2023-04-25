package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.model.AgentState;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgentMother {

  public static Agent.AgentBuilder complete() {
    return Agent.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .name(Constants.instanceName)
      .manufacturer(Constants.instanceManufacturer)
      .state(Constants.instanceState);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String instanceManufacturer = "manufacturer";

    public static final AgentState instanceState = AgentStateMother.complete().build();

  }

}