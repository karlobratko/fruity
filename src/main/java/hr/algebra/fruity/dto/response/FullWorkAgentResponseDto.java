package hr.algebra.fruity.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullWorkAgentResponseDto(
  WorkResponseDto work,
  AgentResponseDto agent,
  BigDecimal agentQuantity,
  UnitOfMeasureResponseDto agentUnitOfMeasure,
  BigDecimal costPerUnitOfMeasure,
  BigDecimal waterQuantity,
  UnitOfMeasureResponseDto waterUnitOfMeasure,
  String note
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String work = "work";

    public static final String agent = "agent";

    public static final String agentQuantity = "agentQuantity";

    public static final String agentUnitOfMeasure = "agentUnitOfMeasure";

    public static final String costPerUnitOfMeasure = "costPerUnitOfMeasure";

    public static final String waterQuantity = "waterQuantity";

    public static final String waterUnitOfMeasure = "waterUnitOfMeasure";

    public static final String note = "note";

  }

}
