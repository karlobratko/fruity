package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkAgentResponseDto;
import hr.algebra.fruity.model.WorkAgent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkAgentToWorkAgentResponseDtoConverter implements Converter<WorkAgent, WorkAgentResponseDto> {

  private final AgentToAgentResponseDtoConverter agentConverter;

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public WorkAgentResponseDto convert(@NonNull WorkAgent source) {
    return new WorkAgentResponseDto(
      source.getWork().getId(),
      agentConverter.convert(source.getAgent()),
      source.getAgentQuantity(),
      unitOfMeasureConverter.convert(source.getAgentUnitOfMeasure()),
      source.getCostPerUnitOfMeasure(),
      source.getWaterQuantity(),
      unitOfMeasureConverter.convert(source.getWaterUnitOfMeasure())
    );
  }

}
