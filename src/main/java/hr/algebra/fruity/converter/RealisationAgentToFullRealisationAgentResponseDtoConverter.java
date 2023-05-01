package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRealisationAgentResponseDto;
import hr.algebra.fruity.model.RealisationAgent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationAgentToFullRealisationAgentResponseDtoConverter implements Converter<RealisationAgent, FullRealisationAgentResponseDto> {

  private final RealisationToRealisationResponseDtoConverter realisationConverter;

  private final AgentToAgentResponseDtoConverter agentConverter;

  private final UnitOfMeasureToUnitOfMeasureResponseDtoConverter unitOfMeasureConverter;

  @Override
  public FullRealisationAgentResponseDto convert(@NonNull RealisationAgent source) {
    return new FullRealisationAgentResponseDto(
      realisationConverter.convert(source.getRealisation()),
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
