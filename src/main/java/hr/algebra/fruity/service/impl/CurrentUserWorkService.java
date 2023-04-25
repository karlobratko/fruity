package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkMapper;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.repository.WorkRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.CreateWorkRequestDtoValidator;
import hr.algebra.fruity.validator.WorkWithUpdateWorkRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkService implements WorkService {

  private final ConversionService conversionService;

  private final CreateWorkRequestDtoValidator createWorkRequestDtoValidator;

  private final WorkWithUpdateWorkRequestDtoValidator workWithUpdateWorkRequestDtoValidator;

  private final WorkMapper workMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkRepository workRepository;

  @Override
  public List<WorkResponseDto> getAllWorks() {
    return workRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(work -> conversionService.convert(work, WorkResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkResponseDto getWorkById(Long id) {
    return conversionService.convert(getById(id), FullWorkResponseDto.class);
  }

  @Override
  @Transactional
  public FullWorkResponseDto createWork(CreateWorkRequestDto requestDto) {
    createWorkRequestDtoValidator.validate(requestDto);

    val work = workRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, Work.class)));

    return conversionService.convert(work, FullWorkResponseDto.class);
  }

  @Override
  @Transactional
  public FullWorkResponseDto updateWorkById(Long id, UpdateWorkRequestDto requestDto) {
    val work = getById(id);

    workWithUpdateWorkRequestDtoValidator.validate(work, requestDto);

    return conversionService.convert(
      workRepository.save(
        workMapper.partialUpdate(work, requestDto)
      ),
      FullWorkResponseDto.class
    );
  }

  @Override
  @Transactional
  public void deleteWorkById(Long id) {
    workRepository.delete(getById(id));
  }

  @Override
  public Work getById(Long id) {
    val work = workRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(work.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return work;
  }

}
