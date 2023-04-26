package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDto;
import hr.algebra.fruity.dto.request.CreateWorkAgentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.request.UpdateWorkAgentRequestDto;
import hr.algebra.fruity.dto.response.FullWorkAgentResponseDto;
import hr.algebra.fruity.dto.response.WorkAgentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkAgentMapper;
import hr.algebra.fruity.model.WorkAgent;
import hr.algebra.fruity.repository.WorkAgentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkAgentService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.CreateWorkAgentRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkAgentService implements WorkAgentService {

  private final ConversionService conversionService;

  private final CreateWorkAgentRequestDtoWithWorkAdapterValidator createWorkAgentRequestDtoWithWorkAdapterValidator;

  private final WorkAgentMapper workAgentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkAgentRepository workAgentRepository;

  private final WorkService workService;

  @Override
  public List<WorkAgentResponseDto> getAllWorkAgentsByWorkId(Long workFk) {
    return workAgentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(workAgent -> conversionService.convert(workAgent, WorkAgentResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkAgentResponseDto getWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk) {
    return conversionService.convert(getByWorkIdAndAgentId(workFk, agentFk), FullWorkAgentResponseDto.class);
  }

  @Override
  public FullWorkAgentResponseDto createWorkAgentForWorkId(Long workFk, CreateWorkAgentRequestDto requestDto) {
    val work = workService.getById(workFk);

    val requestDtoWithWork = new CreateWorkAgentRequestDtoWithWorkAdapter(requestDto, work);
    createWorkAgentRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workAgent = workAgentRepository.save(Objects.requireNonNull(conversionService.convert(requestDtoWithWork, WorkAgent.class)));
    return conversionService.convert(workAgent, FullWorkAgentResponseDto.class);
  }

  @Override
  public FullWorkAgentResponseDto updateWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk, UpdateWorkAgentRequestDto requestDto) {
    val workAgent = getByWorkIdAndAgentId(workFk, agentFk);

    return conversionService.convert(
      workAgentRepository.save(
        workAgentMapper.partialUpdate(workAgent, requestDto)
      ),
      FullWorkAgentResponseDto.class
    );
  }

  @Override
  public void deleteWorkAgentByWorkIdAndAgentId(Long workFk, Integer agentFk) {
    workAgentRepository.delete(getByWorkIdAndAgentId(workFk, agentFk));
  }

  @Override
  public WorkAgent getByWorkIdAndAgentId(Long workFk, Integer agentFk) {
    val workAgent = workAgentRepository.findByWorkIdAndAgentId(workFk, agentFk)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(workAgent.getWork().getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return workAgent;
  }

}
