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
import hr.algebra.fruity.repository.RealisationRowRepository;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.repository.WorkRowRepository;
import hr.algebra.fruity.service.ArcodeParcelService;
import hr.algebra.fruity.service.CadastralParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RowClusterService;
import hr.algebra.fruity.service.RowService;
import hr.algebra.fruity.utils.IntegerUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Session;
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

  private final WorkRowRepository workRowRepository;

  private final RealisationRowRepository realisationRowRepository;

  private final RowClusterService rowClusterService;

  private final ArcodeParcelService arcodeParcelService;

  private final CadastralParcelService cadastralParcelService;

  private final EntityManager entityManager;

  @Override
  public List<RowResponseDto> getAllRows() {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val rows = rowRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toRowResponseDtoConverter::convert)
      .toList();

    session.disableFilter("isNotDeleted");

    return rows;
  }

  @Override
  public FullRowResponseDto getRowById(Long id) {
    return toFullRowResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public FullRowResponseDto createRow(CreateRowRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRowRequestDtoConverter.convert(requestDto));

    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    if (rowRepository.existsByOrdinalAndRowCluster(joinedRequestDto.ordinal(), joinedRequestDto.rowCluster()))
      mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(joinedRequestDto.ordinal(), joinedRequestDto.rowCluster(), IntegerUtils::increment);

    val row = rowRepository.save(Objects.requireNonNull(fromJoinedCreateRowRequestDtoConverter.convert(joinedRequestDto)));

    session.disableFilter("isNotDeleted");

    return toFullRowResponseDtoConverter.convert(row);
  }

  @Override
  @Transactional
  public FullRowResponseDto updateRowById(Long id, UpdateRowRequestDto requestDto) {
    val row = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateRowRequestDtoConverter.convert(requestDto));

    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

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

    val savedRow = rowRepository.save(rowMapper.partialUpdate(row, joinedRequestDto));

    session.disableFilter("isNotDeleted");

    return toFullRowResponseDtoConverter.convert(savedRow);
  }

  @Override
  @Transactional
  public void deleteRowById(Long id) {
    val row = getById(id);

    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    workRowRepository.deleteByRowAndWorkFinishedFalse(row);
    realisationRowRepository.deleteByRowAndRealisationWorkFinishedFalse(row);

    mapOrdinalWhereOrdinalGreaterThanEqualAndRowCluster(row.getOrdinal() + 1, row.getRowCluster(), IntegerUtils::decrement);

    rowRepository.delete(row);

    session.disableFilter("isNotDeleted");
  }

  @Override
  public Row getById(Long id) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val row = rowRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Red"));

    session.disableFilter("isNotDeleted");

    return row;
  }

  @Override
  public Row getByIdIgnoreSoftDelete(Long id) {
    return rowRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Red"));
  }

  @Override
  public List<Row> getAllById(List<Long> ids) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val rows = rowRepository.findAllByIdsAndUserId(ids, currentRequestUserService.getUserId());

    session.disableFilter("isNotDeleted");

    return rows;
  }

  @Override
  public List<Row> getAllByRowClusterId(Long rowClusterFk) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val rows = rowRepository.findAllByRowClusterAndUserId(rowClusterService.getById(rowClusterFk), currentRequestUserService.getUserId());

    session.disableFilter("isNotDeleted");

    return rows;
  }

  @Override
  public List<Row> getAllByArcodeParcelId(Long arcodeParcelFk) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val rows = rowRepository.findAllByArcodeParcelAndUserId(arcodeParcelService.getById(arcodeParcelFk), currentRequestUserService.getUserId());

    session.disableFilter("isNotDeleted");

    return rows;
  }

  @Override
  public List<Row> getAllByCadastralParcelId(Long cadastralParcelFk) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val rows = rowRepository.findAllByCadastralParcelAndUserId(cadastralParcelService.getById(cadastralParcelFk), currentRequestUserService.getUserId());

    session.disableFilter("isNotDeleted");

    return rows;
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
