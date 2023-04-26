package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.dto.response.WorkRowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.exception.NoNewRowsForWorkRowCreationException;
import hr.algebra.fruity.mapper.WorkRowMapper;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkRow;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkRowService;
import hr.algebra.fruity.service.WorkService;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkRowService implements WorkRowService {

  private final ConversionService conversionService;

  private final WorkRowMapper workRowMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkRowRepository workRowRepository;

  private final RowRepository rowRepository;

  private final WorkService workService;

  @Override
  public List<WorkRowResponseDto> getAllWorkRowsByWorkId(Long workFk) {
    return workRowRepository.findAllByWorkOrderByRowRowClusterAscRowOrdinalAsc(workService.getById(workFk)).stream()
      .map(workRow -> conversionService.convert(workRow, WorkRowResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkRowResponseDto getWorkRowByWorkIdAndRowId(Long workFk, Long rowFk) {
    return conversionService.convert(getByWorkIdAndRowId(workFk, rowFk), FullWorkRowResponseDto.class);
  }

  @Override
  public void createWorkRowForWorkId(Long workFk, CreateWorkRowRequestDto requestDto) {
    val work = workService.getById(workFk);

    val rows = setOfRowsFromRequestDto(requestDto);
    removeExistingRowsInWork(rows, work);

    if (rows.isEmpty())
      throw new NoNewRowsForWorkRowCreationException();

    workRowRepository.saveAll(
      rows.stream()
        .map(row -> WorkRow.builder().work(work).row(row).note(requestDto.note()).build())
        .toList()
    );
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

  private Set<Row> setOfRowsFromRequestDto(CreateWorkRowRequestDto requestDto) {
    Set<Row> rows = new HashSet<>();

    if (Objects.nonNull(requestDto.rowFk()))
      rowRepository.findById(requestDto.rowFk()).ifPresent(rows::add);

    if (Objects.nonNull(requestDto.rowFks()))
      rows.addAll(rowRepository.findAllById(requestDto.rowFks()));

    if (Objects.nonNull(requestDto.rowClusterFk()))
      rows.addAll(rowRepository.findAllByRowClusterId(requestDto.rowClusterFk()));

    if (Objects.nonNull(requestDto.arcodeParcelFk()))
      rows.addAll(rowRepository.findAllByRowClusterArcodeParcelId(requestDto.arcodeParcelFk()));

    if (Objects.nonNull(requestDto.cadastralParcelFk()))
      rows.addAll(rowRepository.findAllByRowClusterArcodeParcelCadastralParcelId(requestDto.cadastralParcelFk()));

    return rows;
  }

  private void removeExistingRowsInWork(Set<Row> rows, Work work) {
    rows.removeAll(
      workRowRepository.findAllByWorkId(work.getId()).stream()
        .map(WorkRow::getRow)
        .collect(Collectors.toUnmodifiableSet())
    );
  }

}
