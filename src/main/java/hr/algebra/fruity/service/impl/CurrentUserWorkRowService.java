package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import hr.algebra.fruity.dto.request.CreateWorkRowRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.dto.response.WorkRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkRowMapper;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkRowService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.CreateWorkRowRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkRowService implements WorkRowService {

  private final ConversionService conversionService;

  private final CreateWorkRowRequestDtoWithWorkAdapterValidator createWorkRowRequestDtoWithWorkAdapterValidator;

  private final WorkRowMapper workRowMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkRowRepository workRowRepository;

  private final WorkService workService;

  @Override
  public List<WorkRowResponseDto> getAllWorkRowsByWorkId(Long workFk) {
    return workRowRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(workRow -> conversionService.convert(workRow, WorkRowResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkRowResponseDto getWorkRowByWorkIdAndRowId(Long workFk, Long rowFk) {
    return conversionService.convert(getByWorkIdAndRowId(workFk, rowFk), FullWorkRowResponseDto.class);
  }

  @Override
  public FullWorkRowResponseDto createWorkRowForWorkId(Long workFk, CreateWorkRowRequestDto requestDto) {
    val work = workService.getById(workFk);

    val requestDtoWithWork = new CreateWorkRowRequestDtoWithWorkAdapter(requestDto, work);
    createWorkRowRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workRow = workRowRepository.save(Objects.requireNonNull(conversionService.convert(requestDtoWithWork, WorkRow.class)));
    return conversionService.convert(workRow, FullWorkRowResponseDto.class);
  }

  @Override
  public FullWorkRowResponseDto updateWorkRowByWorkIdAndRowId(Long workFk, Long rowFk, UpdateWorkRowRequestDto requestDto) {
    val workRow = getByWorkIdAndRowId(workFk, rowFk);

    return conversionService.convert(
      workRowRepository.save(
        workRowMapper.partialUpdate(workRow, requestDto)
      ),
      FullWorkRowResponseDto.class
    );
  }

  @Override
  public void deleteWorkRowByWorkIdAndRowId(Long workFk, Long rowFk) {
    workRowRepository.delete(getByWorkIdAndRowId(workFk, rowFk));
  }

  @Override
  public WorkRow getByWorkIdAndRowId(Long workFk, Long rowFk) {
    val workRow = workRowRepository.findByWorkIdAndRowId(workFk, rowFk)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(workRow.getWork().getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return workRow;
  }

}
