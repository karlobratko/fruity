package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.RowMapper;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.repository.RowRepository;
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
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRowService implements RowService {

  private final ConversionService conversionService;

  private final RowMapper rowMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RowRepository rowRepository;

  private final RowClusterService rowClusterService;

  @Override
  public List<RowResponseDto> getAllRows() {
    return rowRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(row -> conversionService.convert(row, RowResponseDto.class))
      .toList();
  }

  @Override
  public List<RowResponseDto> getAllRowsByRowClusterId(Long rowClusterId) {
    return rowRepository.findAllByRowClusterOrderByOrdinalAsc(rowClusterService.getById(rowClusterId)).stream()
      .map(row -> conversionService.convert(row, RowResponseDto.class))
      .toList();
  }

  @Override
  public FullRowResponseDto getRowById(Long id) {
    return conversionService.convert(getById(id), FullRowResponseDto.class);

  }

  @Override
  @Transactional
  public FullRowResponseDto createRow(CreateRowRequestDto requestDto) {
    if (rowRepository.existsByOrdinalAndRowClusterId(requestDto.ordinal(), requestDto.rowClusterFk()))
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowClusterId(requestDto.ordinal(), requestDto.rowClusterFk(), IntegerUtils::increment);

    val row = rowRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, Row.class)));
    return conversionService.convert(row, FullRowResponseDto.class);
  }

  @Override
  @Transactional
  public FullRowResponseDto updateRowById(Long id, UpdateRowRequestDto requestDto) {
    val row = getById(id);

    if (Objects.nonNull(requestDto.ordinal()) && rowRepository.existsByOrdinalAndRowClusterId(requestDto.ordinal(), Objects.requireNonNullElse(requestDto.rowClusterFk(), row.getRowCluster().getId()))) {
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowClusterId(
        requestDto.ordinal(),
        Objects.requireNonNullElse(requestDto.rowClusterFk(), row.getRowCluster().getId()),
        IntegerUtils::increment
      );
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowClusterId(
        row.getOrdinal() + 1,
        Objects.requireNonNullElse(requestDto.rowClusterFk(), row.getRowCluster().getId()),
        IntegerUtils::decrement
      );
    }

    return conversionService.convert(
      rowRepository.save(
        rowMapper.partialUpdate(row, requestDto)
      ),
      FullRowResponseDto.class
    );
  }

  @Override
  @Transactional
  public void deleteRowById(Long id) {
    val row = getById(id);

    mapOrdinalWhereOrdinalGreaterThanEqualAndRowClusterId(row.getOrdinal() + 1, row.getRowCluster().getId(), IntegerUtils::decrement);

    rowRepository.delete(row);
  }

  @Override
  public Row getById(Long id) {
    val row = rowRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(row.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return row;
  }

  private void mapOrdinalWhereOrdinalGreaterThanEqualAndRowClusterId(Integer ordinal, Long rowClusterId, IntUnaryOperator mapper) {
    val counter = new AtomicInteger(ordinal);
    val rowsToUpdate = rowRepository.findByOrdinalGreaterThanEqualAndRowClusterIdOrderByOrdinalAsc(ordinal, rowClusterId).stream()
      .filter(it -> it.getOrdinal().equals(counter.getAndIncrement()))
      .peek(it -> it.setOrdinal(mapper.applyAsInt(it.getOrdinal())))
      .toList();

    if (!rowsToUpdate.isEmpty())
      rowRepository.saveAll(rowsToUpdate);
  }

}
