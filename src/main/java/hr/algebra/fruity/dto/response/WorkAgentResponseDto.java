package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record WorkAgentResponseDto(
  Long workFk,
  AgentResponseDto agent,
  BigDecimal agentQuantity,
  UnitOfMeasureResponseDto agentUnitOfMeasure,
  BigDecimal costPerUnitOfMeasure,
  BigDecimal waterQuantity,
  UnitOfMeasureResponseDto waterUnitOfMeasure
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String workFk = "workFk";

    public static final String agent = "agent";

    public static final String agentQuantity = "agentQuantity";

    public static final String agentUnitOfMeasure = "agentUnitOfMeasure";

    public static final String costPerUnitOfMeasure = "costPerUnitOfMeasure";

    public static final String waterQuantity = "waterQuantity";

    public static final String waterUnitOfMeasure = "waterUnitOfMeasure";

  }

}
