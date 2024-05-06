package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.model.Agent;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AgentToAgentResponseDtoConverter implements Converter<Agent, AgentResponseDto> {

  @Override
  public AgentResponseDto convert(@NonNull Agent source) {
    return new AgentResponseDto(
      source.getId(),
      source.getName(),
      source.getManufacturer(),
      source.getState().getDisplayName()
    );
  }

}
