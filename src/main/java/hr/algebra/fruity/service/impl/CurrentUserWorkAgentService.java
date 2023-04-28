package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateWorkAgentRequestDtoToJoinedCreateWorkAgentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter;
import hr.algebra.fruity.converter.WorkAgentToFullWorkAgentResponseDtoConverter;
import hr.algebra.fruity.converter.WorkAgentToWorkAgentResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkAgentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkAgentResponseDto;
import hr.algebra.fruity.dto.response.WorkAgentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkAgentMapper;
import hr.algebra.fruity.model.WorkAgent;
import hr.algebra.fruity.repository.WorkAgentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkAgentService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.JoinedCreateWorkAgentRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkAgentService implements WorkAgentService {

  private final WorkAgentToWorkAgentResponseDtoConverter toWorkAgentResponseDtoConverter;

  private final WorkAgentToFullWorkAgentResponseDtoConverter toFullWorkAgentResponseDtoConverter;

  private final CreateWorkAgentRequestDtoToJoinedCreateWorkAgentRequestDtoConverter toJoinedCreateWorkAgentRequestDtoConverter;

  private final JoinedCreateWorkAgentRequestDtoWithWorkAdapterToWorkAgentConverter fromJoinedCreateWorkAgentRequestDtoWithWorkAdapterConverter;

  private final JoinedCreateWorkAgentRequestDtoWithWorkAdapterValidator joinedCreateWorkAgentRequestDtoWithWorkAdapterValidator;

  private final WorkAgentMapper workAgentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkAgentRepository workAgentRepository;

  private final WorkService workService;

  @Override
  public List<WorkAgentResponseDto> getAllWorkAgentsByWorkId(Long workFk) {
    return workAgentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(toWorkAgentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkAgentResponseDto getWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk) {
    return toFullWorkAgentResponseDtoConverter.convert(getByWorkIdAndAgentId(workFk, agentFk));
  }

  @Override
  public FullWorkAgentResponseDto createWorkAgentForWorkId(Long workFk, CreateWorkAgentRequestDto requestDto) {
    val work = workService.getById(workFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateWorkAgentRequestDtoConverter.convert(requestDto));

    val requestDtoWithWork = new JoinedCreateWorkAgentRequestDtoWithWorkAdapter(joinedRequestDto, work);
    joinedCreateWorkAgentRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workAgent = workAgentRepository.save(Objects.requireNonNull(fromJoinedCreateWorkAgentRequestDtoWithWorkAdapterConverter.convert(requestDtoWithWork)));
    return toFullWorkAgentResponseDtoConverter.convert(workAgent);
  }

  @Override
  public FullWorkAgentResponseDto updateWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk, UpdateWorkAgentRequestDto requestDto) {
    val workAgent = getByWorkIdAndAgentId(workFk, agentFk);

    return toFullWorkAgentResponseDtoConverter.convert(
      workAgentRepository.save(
        workAgentMapper.partialUpdate(workAgent, requestDto)
      )
    );
  }

  @Override
  public void deleteWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk) {
    workAgentRepository.delete(getByWorkIdAndAgentId(workFk, agentFk));
  }

  @Override
  public WorkAgent getByWorkIdAndAgentId(Long workFk, Integer agentFk) {
    return workAgentRepository.findByWorkIdAndAgentIdAndWorkUserId(workFk, agentFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Sredstvo korišteno u radu"));
  }

}
