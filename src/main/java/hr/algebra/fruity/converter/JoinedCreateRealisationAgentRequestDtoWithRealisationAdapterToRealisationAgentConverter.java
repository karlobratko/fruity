package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.model.RealisationAgent;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterToRealisationAgentConverter implements Converter<JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter, RealisationAgent> {

  @Override
  public RealisationAgent convert(@NonNull JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter source) {
    return RealisationAgent.builder()
      .realisation(source.realisation())
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
