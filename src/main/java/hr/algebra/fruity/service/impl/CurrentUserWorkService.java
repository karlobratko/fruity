package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateWorkRequestDtoToJoinedCreateWorkRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkRequestDtoToWorkConverter;
import hr.algebra.fruity.converter.RealisationToRealisationResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateWorkRequestDtoToJoinedUpdateWorkRequestDtoConverter;
import hr.algebra.fruity.converter.WorkToFullWorkResponseDtoConverter;
import hr.algebra.fruity.converter.WorkToWorkResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkMapper;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.repository.WorkRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.JoinedCreateWorkRequestDtoValidator;
import hr.algebra.fruity.validator.WorkWithJoinedUpdateWorkRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkService implements WorkService {

  private final WorkToWorkResponseDtoConverter toWorkResponseDtoConverter;

  private final WorkToFullWorkResponseDtoConverter toFullWorkResponseDtoConverter;

  private final CreateWorkRequestDtoToJoinedCreateWorkRequestDtoConverter toJoinedCreateWorkRequestDtoConverter;

  private final JoinedCreateWorkRequestDtoToWorkConverter fromJoinedCreateWorkRequestDtoConverter;

  private final UpdateWorkRequestDtoToJoinedUpdateWorkRequestDtoConverter toJoinedUpdateWorkRequestDtoConverter;

  private final RealisationToRealisationResponseDtoConverter toRealisationResponseDtoConverter;

  private final JoinedCreateWorkRequestDtoValidator joinedCreateWorkRequestDtoValidator;

  private final WorkWithJoinedUpdateWorkRequestDtoValidator workWithJoinedUpdateWorkRequestDtoValidator;

  private final WorkMapper workMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkRepository workRepository;

  private final RealisationRepository realisationRepository;

  @Override
  public List<WorkResponseDto> getAllWorks() {
    return workRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toWorkResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public List<RealisationResponseDto> getAllRealisationsByWorkId(Long workFk) {
    return realisationRepository.findAllByWork(getById(workFk)).stream()
      .map(toRealisationResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkResponseDto getWorkById(Long id) {
    return toFullWorkResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public FullWorkResponseDto createWork(CreateWorkRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateWorkRequestDtoConverter.convert(requestDto));

    joinedCreateWorkRequestDtoValidator.validate(joinedRequestDto);

    val work = workRepository.save(Objects.requireNonNull(fromJoinedCreateWorkRequestDtoConverter.convert(joinedRequestDto)));

    return toFullWorkResponseDtoConverter.convert(work);
  }

  @Override
  @Transactional
  public FullWorkResponseDto updateWorkById(Long id, UpdateWorkRequestDto requestDto) {
    val work = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateWorkRequestDtoConverter.convert(requestDto));

    workWithJoinedUpdateWorkRequestDtoValidator.validate(work, joinedRequestDto);

    return toFullWorkResponseDtoConverter.convert(
      workRepository.save(
        workMapper.partialUpdate(work, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteWorkById(Long id) {
    workRepository.delete(getById(id));
  }

  @Override
  public Work getById(Long id) {
    return workRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Rad"));
  }

}
