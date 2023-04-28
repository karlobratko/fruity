package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAgentRequestDto;
import hr.algebra.fruity.service.AgentService;
import hr.algebra.fruity.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAgentRequestDtoToJoinedCreateWorkAgentRequestDtoConverter implements Converter<CreateWorkAgentRequestDto, JoinedCreateWorkAgentRequestDto> {

  private final AgentService agentService;

  private final UnitOfMeasureService unitOfMeasureService;

  @Override
  public JoinedCreateWorkAgentRequestDto convert(CreateWorkAgentRequestDto source) {
    return new JoinedCreateWorkAgentRequestDto(
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
