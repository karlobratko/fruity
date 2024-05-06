package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRealisationAgentRequestDtoToJoinedCreateRealisationAgentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterToRealisationAgentConverter;
import hr.algebra.fruity.converter.RealisationAgentToFullRealisationAgentResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationAgentToRealisationAgentResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationAgentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.dto.response.FullRealisationAgentResponseDto;
import hr.algebra.fruity.dto.response.RealisationAgentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.mapper.RealisationAgentMapper;
import hr.algebra.fruity.model.RealisationAgent;
import hr.algebra.fruity.repository.RealisationAgentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RealisationAgentService;
import hr.algebra.fruity.service.RealisationService;
import hr.algebra.fruity.validator.JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationAgentWithUpdateRealisationAgentRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRealisationAgentService implements RealisationAgentService {

  private final RealisationAgentToRealisationAgentResponseDtoConverter toRealisationAgentResponseDtoConverter;

  private final RealisationAgentToFullRealisationAgentResponseDtoConverter toFullRealisationAgentResponseDtoConverter;

  private final CreateRealisationAgentRequestDtoToJoinedCreateRealisationAgentRequestDtoConverter toJoinedCreateRealisationAgentRequestDtoConverter;

  private final JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterToRealisationAgentConverter fromJoinedCreateRealisationAgentRequestDtoWithRealisationAdapterConverter;

  private final JoinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator joinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator;

  private final RealisationAgentWithUpdateRealisationAgentRequestDtoValidator realisationAgentWithUpdateRealisationAgentRequestDtoValidator;

  private final RealisationAgentMapper realisationAgentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RealisationAgentRepository realisationAgentRepository;

  private final RealisationService realisationService;

  @Override
  public List<RealisationAgentResponseDto> getAllRealisationAgentsByRealisationId(Long realisationFk) {
    return realisationAgentRepository.findAllByRealisation(realisationService.getById(realisationFk)).stream()
      .map(toRealisationAgentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRealisationAgentResponseDto getRealisationAgentByRealisationIdAndAgentId(Long realisationFk, Integer agentFk) {
    return toFullRealisationAgentResponseDtoConverter.convert(getByRealisationIdAndAgentId(realisationFk, agentFk));
  }

  @Override
  public FullRealisationAgentResponseDto createRealisationAgentForRealisationId(Long realisationFk, CreateRealisationAgentRequestDto requestDto) {
    val realisation = realisationService.getById(realisationFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRealisationAgentRequestDtoConverter.convert(requestDto));

    val requestDtoWithRealisation = new JoinedCreateRealisationAgentRequestDtoWithRealisationAdapter(joinedRequestDto, realisation);
    joinedCreateRealisationAgentRequestDtoWithRealisationAdapterValidator.validate(requestDtoWithRealisation);

    val realisationAgent = realisationAgentRepository.save(Objects.requireNonNull(fromJoinedCreateRealisationAgentRequestDtoWithRealisationAdapterConverter.convert(requestDtoWithRealisation)));
    return toFullRealisationAgentResponseDtoConverter.convert(realisationAgent);
  }

  @Override
  public FullRealisationAgentResponseDto updateRealisationAgentByRealisationIdAndAgentId(Long realisationFk, Integer agentFk, UpdateRealisationAgentRequestDto requestDto) {
    val realisationAgent = getByRealisationIdAndAgentId(realisationFk, agentFk);

    realisationAgentWithUpdateRealisationAgentRequestDtoValidator.validate(realisationAgent, requestDto);

    return toFullRealisationAgentResponseDtoConverter.convert(
      realisationAgentRepository.save(
        realisationAgentMapper.partialUpdate(realisationAgent, requestDto)
      )
    );
  }

  @Override
  public void deleteRealisationAgentByRealisationIdAndAgentId(Long realisationFk, Integer agentFk) {
    val realisationAgent = getByRealisationIdAndAgentId(realisationFk, agentFk);

    if (realisationAgent.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    realisationAgentRepository.delete(realisationAgent);
  }

  @Override
  public RealisationAgent getByRealisationIdAndAgentId(Long realisationFk, Integer agentFk) {
    return realisationAgentRepository.findByRealisationIdAndAgentIdAndRealisationWorkUserId(realisationFk, agentFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Sredstvo korišteno u realizaciji"));
  }

}
