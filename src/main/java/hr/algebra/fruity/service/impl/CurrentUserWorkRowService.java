package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateWorkRowRequestDtoToJoinedCreateWorkRowRequestDtoConverter;
import hr.algebra.fruity.converter.WorkRowToFullWorkRowResponseDtoConverter;
import hr.algebra.fruity.converter.WorkRowToWorkRowResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.dto.response.WorkRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkRowMapper;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkRowService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.JoinedCreateWorkRowRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkRowService implements WorkRowService {

  private final WorkRowToWorkRowResponseDtoConverter toWorkRowResponseDtoConverter;

  private final WorkRowToFullWorkRowResponseDtoConverter toFullWorkRowResponseDtoConverter;

  private final CreateWorkRowRequestDtoToJoinedCreateWorkRowRequestDtoConverter toJoinedCreateWorkRowRequestDtoConverter;

  private final JoinedCreateWorkRowRequestDtoValidator joinedCreateWorkRowRequestDtoValidator;

  private final WorkRowMapper workRowMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkRowRepository workRowRepository;

  private final RowRepository rowRepository;

  private final WorkService workService;

  @Override
  public List<WorkRowResponseDto> getAllWorkRowsByWorkId(Long workFk) {
    return workRowRepository.findAllByWorkOrderByRowRowClusterAscRowOrdinalAsc(workService.getById(workFk)).stream()
      .map(toWorkRowResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkRowResponseDto getWorkRowByWorkIdAndRowId(Long workFk, Long rowFk) {
    return toFullWorkRowResponseDtoConverter.convert(getByWorkIdAndRowId(workFk, rowFk));
  }

  @Override
  public void createWorkRowForWorkId(Long workFk, CreateWorkRowRequestDto requestDto) {
    val work = workService.getById(workFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateWorkRowRequestDtoConverter.convert(requestDto));

    removeExistingRowsInWork(joinedRequestDto.rows(), work);

    joinedCreateWorkRowRequestDtoValidator.validate(joinedRequestDto);

    workRowRepository.saveAll(
      joinedRequestDto.rows().stream()
        .map(row -> WorkRow.builder().work(work).row(row).note(requestDto.note()).build())
        .toList()
    );
  }

  @Override
  public FullWorkRowResponseDto updateWorkRowByWorkIdAndRowId(Long workFk, Long rowFk, UpdateWorkRowRequestDto requestDto) {
    val workRow = getByWorkIdAndRowId(workFk, rowFk);

    return toFullWorkRowResponseDtoConverter.convert(
      workRowRepository.save(
        workRowMapper.partialUpdate(workRow, requestDto)
      )
    );
  }

  @Override
  public void deleteWorkRowByWorkIdAndRowId(Long workFk, Long rowFk) {
    workRowRepository.delete(getByWorkIdAndRowId(workFk, rowFk));
  }

  @Override
  public WorkRow getByWorkIdAndRowId(Long workFk, Long rowFk) {
    return workRowRepository.findByWorkIdAndRowIdAndWorkUserId(workFk, rowFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Red uključen u radu"));
  }

  private void removeExistingRowsInWork(Set<Row> rows, Work work) {
    rows.removeAll(
      workRowRepository.findAllByWork(work).stream()
        .map(WorkRow::getRow)
        .collect(Collectors.toUnmodifiableSet())
    );
  }

}
