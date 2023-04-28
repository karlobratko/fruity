package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.AgentToAgentResponseDtoConverter;
import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.repository.AgentRepository;
import hr.algebra.fruity.service.AgentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

  private final AgentToAgentResponseDtoConverter toAgentResponseDtoConverter;

  private final AgentRepository agentRepository;

  @Override
  public List<AgentResponseDto> getAllAgents() {
    return agentRepository.findAll().stream()
      .map(toAgentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public AgentResponseDto getAgentById(Integer id) {
    return toAgentResponseDtoConverter.convert(getById(id));
  }

  @Override
  public Agent getById(Integer id) {
    return agentRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Sredstvo"));
  }

}
