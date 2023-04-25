package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.model.Agent;
import java.util.List;

public interface AgentService {

  List<AgentResponseDto> getAllAgents();

  AgentResponseDto getAgentById(Integer id);

  Agent getById(Integer id);

}
