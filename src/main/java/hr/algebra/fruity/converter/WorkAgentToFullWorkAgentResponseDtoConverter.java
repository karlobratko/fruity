package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkAgentResponseDto;
import hr.algebra.fruity.model.WorkAgent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkAgentToFullWorkAgentResponseDtoConverter implements Converter<WorkAgent, FullWorkAgentResponseDto> {

  private final WorkToWorkResponseDtoConverter workConverter;

  private final AgentToAgentResponseDtoConverter agentConverter;

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public FullWorkAgentResponseDto convert(@NonNull WorkAgent source) {
    return new FullWorkAgentResponseDto(
      workConverter.convert(source.getWork()),
      agentConverter.convert(source.getAgent()),
      source.getAgentQuantity(),
      unitOfMeasureConverter.convert(source.getAgentUnitOfMeasure()),
      source.getCostPerUnitOfMeasure(),
      source.getWaterQuantity(),
      unitOfMeasureConverter.convert(source.getWaterUnitOfMeasure()),
      source.getNote()
    );
  }

}
