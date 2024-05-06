package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkAgentRequestDto;
import hr.algebra.fruity.dto.response.FullWorkAgentResponseDto;
import hr.algebra.fruity.dto.response.WorkAgentResponseDto;
import hr.algebra.fruity.model.WorkAgent;
import java.util.List;

public interface WorkAgentService {

  List<WorkAgentResponseDto> getAllWorkAgentsByWorkId(Long workFk);

  FullWorkAgentResponseDto getWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk);

  FullWorkAgentResponseDto createWorkAgentForWorkId(Long workFk, CreateWorkAgentRequestDto requestDto);

  FullWorkAgentResponseDto updateWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk, UpdateWorkAgentRequestDto requestDto);

  void deleteWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk);

  WorkAgent getByWorkIdAndAgentId(Long workFk, Integer agentFk);

}
