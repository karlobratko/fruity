package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RealisationAgentResponseDto;
import hr.algebra.fruity.model.RealisationAgent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationAgentToRealisationAgentResponseDtoConverter implements Converter<RealisationAgent, RealisationAgentResponseDto> {

  private final AgentToAgentResponseDtoConverter agentConverter;

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public RealisationAgentResponseDto convert(@NonNull RealisationAgent source) {
    return new RealisationAgentResponseDto(
      source.getRealisation().getId(),
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
