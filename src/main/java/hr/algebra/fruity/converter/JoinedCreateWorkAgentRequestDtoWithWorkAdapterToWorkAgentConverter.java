package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.model.WorkAgent;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter implements Converter<JoinedCreateWorkAgentRequestDtoWithWorkAdapter, WorkAgent> {

  @Override
  public WorkAgent convert(@NonNull JoinedCreateWorkAgentRequestDtoWithWorkAdapter source) {
    return WorkAgent.builder()
      .work(source.work())
      .agent(source.dto().agent())
      .agentQuantity(source.dto().agentQuantity())
      .agentUnitOfMeasure(source.dto().agentUnitOfMeasure())
      .costPerUnitOfMeasure(source.dto().costPerUnitOfMeasure())
      .waterQuantity(source.dto().waterQuantity())
      .waterUnitOfMeasure(source.dto().waterUnitOfMeasure())
      .note(source.dto().note())
      .build();
  }

}
