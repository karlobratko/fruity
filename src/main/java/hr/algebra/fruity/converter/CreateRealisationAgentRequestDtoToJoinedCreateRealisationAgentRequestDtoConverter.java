package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAgentRequestDto;
import hr.algebra.fruity.service.AgentService;
import hr.algebra.fruity.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRealisationAgentRequestDtoToJoinedCreateRealisationAgentRequestDtoConverter implements Converter<CreateRealisationAgentRequestDto, JoinedCreateRealisationAgentRequestDto> {

  private final AgentService agentService;

  private final UnitOfMeasureService unitOfMeasureService;

  @Override
  public JoinedCreateRealisationAgentRequestDto convert(CreateRealisationAgentRequestDto source) {
    return new JoinedCreateRealisationAgentRequestDto(
      agentService.getById(source.agentFk()),
      source.agentQuantity(),
      unitOfMeasureService.getById(source.agentUnitOfMeasureFk()),
      source.costPerUnitOfMeasure(),
      source.waterQuantity(),
      unitOfMeasureService.getById(source.waterUnitOfMeasureFk()),
      source.note()
    );
  }

}
