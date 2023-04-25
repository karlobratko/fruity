package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.repository.AgentRepository;
import hr.algebra.fruity.service.AgentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

  private final ConversionService conversionService;

  private final AgentRepository agentRepository;

  @Override
  public List<AgentResponseDto> getAllAgents() {
    return agentRepository.findAll().stream()
      .map(agent -> conversionService.convert(agent, AgentResponseDto.class))
      .toList();
  }

  @Override
  public AgentResponseDto getAgentById(Integer id) {
    return conversionService.convert(getById(id), AgentResponseDto.class);
  }

  @Override
  public Agent getById(Integer id) {
    val agent = agentRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return agent;
  }

}
