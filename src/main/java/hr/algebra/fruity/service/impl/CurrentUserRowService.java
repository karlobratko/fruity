package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRowRequestDtoToJoinedCreateRowRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRowRequestDtoToRowConverter;
import hr.algebra.fruity.converter.RowToFullRowResponseDtoConverter;
import hr.algebra.fruity.converter.RowToRowResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateRowRequestDtoToJoinedUpdateRowRequestDtoConverter;
import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RowMapper;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.service.ArcodeParcelService;
import hr.algebra.fruity.service.CadastralParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RowClusterService;
import hr.algebra.fruity.service.RowService;
import hr.algebra.fruity.utils.IntegerUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRowService implements RowService {

  private final RowToRowResponseDtoConverter toRowResponseDtoConverter;

  private final RowToFullRowResponseDtoConverter toFullRowResponseDtoConverter;

  private final CreateRowRequestDtoToJoinedCreateRowRequestDtoConverter toJoinedCreateRowRequestDtoConverter;

  private final JoinedCreateRowRequestDtoToRowConverter fromJoinedCreateRowRequestDtoConverter;

  private final UpdateRowRequestDtoToJoinedUpdateRowRequestDtoConverter toJoinedUpdateRowRequestDtoConverter;

  private final RowMapper rowMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RowRepository rowRepository;

  private final RowClusterService rowClusterService;

  private final ArcodeParcelService arcodeParcelService;

  private final CadastralParcelService cadastralParcelService;

  @Override
  public List<RowResponseDto> getAllRows() {
    return rowRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toRowResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRowResponseDto getRowById(Long id) {
    return toFullRowResponseDtoConverter.convert(getById(id));

  }

  @Override
  @Transactional
  public FullRowResponseDto createRow(CreateRowRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRowRequestDtoConverter.convert(requestDto));

    if (rowRepository.existsByOrdinalAndRowCluster(joinedRequestDto.ordinal(), joinedRequestDto.rowCluster()))
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(joinedRequestDto.ordinal(), joinedRequestDto.rowCluster(), IntegerUtils::increment);

    val row = rowRepository.save(Objects.requireNonNull(fromJoinedCreateRowRequestDtoConverter.convert(joinedRequestDto)));
    return toFullRowResponseDtoConverter.convert(row);
  }

  @Override
  @Transactional
  public FullRowResponseDto updateRowById(Long id, UpdateRowRequestDto requestDto) {
    val row = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateRowRequestDtoConverter.convert(requestDto));

    if (Objects.nonNull(joinedRequestDto.ordinal()) && rowRepository.existsByOrdinalAndRowCluster(joinedRequestDto.ordinal(), row.getRowCluster())) {
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(
        joinedRequestDto.ordinal(),
        row.getRowCluster(),
        IntegerUtils::increment
      );
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(
        row.getOrdinal() + 1,
        row.getRowCluster(),
        IntegerUtils::decrement
      );
    }

    return toFullRowResponseDtoConverter.convert(
      rowRepository.save(
        rowMapper.partialUpdate(row, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteRowById(Long id) {
    val row = getById(id);

    mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(row.getOrdinal() + 1, row.getRowCluster(), IntegerUtils::decrement);

    rowRepository.delete(row);
  }

  @Override
  public Row getById(Long id) {
    return rowRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Red"));
  }

  @Override
  public List<Row> getAllById(List<Long> ids) {
    return rowRepository.findAllByIdsAndUserId(ids, currentRequestUserService.getUserId());
  }

  @Override
  public List<Row> getAllByRowClusterId(Long rowClusterFk) {
    return rowRepository.findAllByRowClusterAndUserId(rowClusterService.getById(rowClusterFk), currentRequestUserService.getUserId());
  }

  @Override
  public List<Row> getAllByArcodeParcelId(Long arcodeParcelFk) {
    return rowRepository.findAllByArcodeParcelAndUserId(arcodeParcelService.getById(arcodeParcelFk), currentRequestUserService.getUserId());
  }

  @Override
  public List<Row> getAllByCadastralParcelId(Long cadastralParcelFk) {
    return rowRepository.findAllByCadastralParcelAndUserId(cadastralParcelService.getById(cadastralParcelFk), currentRequestUserService.getUserId());
  }

  private void mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(Integer ordinal, RowCluster rowCluster, IntUnaryOperator mapper) {
    val counter = new AtomicInteger(ordinal);
    val rowsToUpdate = rowRepository.findByOrdinalGreaterThanEqualAndRowClusterOrderByOrdinalAsc(ordinal, rowCluster).stream()
      .filter(it -> it.getOrdinal().equals(counter.getAndIncrement()))
      .peek(it -> it.setOrdinal(mapper.applyAsInt(it.getOrdinal())))
      .toList();

    if (!rowsToUpdate.isEmpty())
      rowRepository.saveAll(rowsToUpdate);
  }

}
