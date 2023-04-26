package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkAgent;
import hr.algebra.fruity.repository.AgentRepository;
import hr.algebra.fruity.repository.UnitOfMeasureRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter implements Converter<CreateWorkAgentRequestDtoWithWorkAdapter, WorkAgent> {

  private final AgentRepository agentRepository;

  private final UnitOfMeasureRepository unitOfMeasureRepository;

  @Override
  public WorkAgent convert(@NonNull CreateWorkAgentRequestDtoWithWorkAdapter source) {
    return WorkAgent.builder()
      .work(source.work())
      .agent(agentRepository.findById(source.dto().agentFk()).orElseThrow(EntityNotFoundException::new))
      .agentQuantity(source.dto().agentQuantity())
      .agentUnitOfMeasure(unitOfMeasureRepository.findById(source.dto().agentUnitOfMeasureFk()).orElseThrow(EntityNotFoundException::new))
      .costPerUnitOfMeasure(source.dto().costPerUnitOfMeasure())
      .waterQuantity(source.dto().waterQuantity())
      .waterUnitOfMeasure(unitOfMeasureRepository.findById(source.dto().waterUnitOfMeasureFk()).orElseThrow(EntityNotFoundException::new))
      .note(source.dto().note())
      .build();
  }

}
