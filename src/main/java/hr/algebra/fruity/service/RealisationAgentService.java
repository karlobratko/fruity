package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationAgentResponseDto;
import hr.algebra.fruity.dto.response.RealisationAgentResponseDto;
import hr.algebra.fruity.model.RealisationAgent;
import java.util.List;

public interface RealisationAgentService {

  List<RealisationAgentResponseDto> getAllRealisationAgentsByRealisationId(Long realisationFk);

  FullRealisationAgentResponseDto getRealisationAgentByRealisationIdAndAgentId(Long realisationFk, Integer agentFk);

  FullRealisationAgentResponseDto createRealisationAgentForRealisationId(Long realisationFk, CreateRealisationAgentRequestDto requestDto);

  FullRealisationAgentResponseDto updateRealisationAgentByRealisationIdAndAgentId(Long realisationFk, Integer agentFk, UpdateRealisationAgentRequestDto requestDto);

  void deleteRealisationAgentByRealisationIdAndAgentId(Long realisationFk, Integer agentFk);

  RealisationAgent getByRealisationIdAndAgentId(Long realisationFk, Integer agentFk);

}
