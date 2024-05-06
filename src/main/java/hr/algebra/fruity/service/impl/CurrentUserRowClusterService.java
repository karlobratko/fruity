package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRowClusterRequestDtoToJoinedCreateRowClusterRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRowClusterRequestDtoToRowClusterConverter;
import hr.algebra.fruity.converter.RowClusterToFullRowClusterResponseDtoConverter;
import hr.algebra.fruity.converter.RowClusterToRowClusterResponseDtoConverter;
import hr.algebra.fruity.converter.RowToRowResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateRowClusterRequestDtoToJoinedUpdateRowClusterRequestDtoConverter;
import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RowClusterMapper;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RowClusterService;
import hr.algebra.fruity.validator.JoinedCreateRowClusterRequestDtoValidator;
import hr.algebra.fruity.validator.RowClusterWithJoinedUpdateRowClusterRequestDtoValidator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRowClusterService implements RowClusterService {

  private final RowClusterToRowClusterResponseDtoConverter toRowClusterResponseDtoConverter;

  private final RowClusterToFullRowClusterResponseDtoConverter toFullRowClusterResponseDtoConverter;

  private final CreateRowClusterRequestDtoToJoinedCreateRowClusterRequestDtoConverter toJoinedCreateRowClusterRequestDtoConverter;

  private final JoinedCreateRowClusterRequestDtoToRowClusterConverter fromJoinedCreateRowClusterRequestDtoConverter;

  private final UpdateRowClusterRequestDtoToJoinedUpdateRowClusterRequestDtoConverter toJoinedUpdateRowClusterRequestDtoConverter;

  private final RowToRowResponseDtoConverter toRowResponseDtoConverter;

  private final JoinedCreateRowClusterRequestDtoValidator joinedCreateRowClusterRequestDtoValidator;

  private final RowClusterWithJoinedUpdateRowClusterRequestDtoValidator rowClusterWithJoinedUpdateRowClusterRequestDtoValidator;

  private final RowClusterMapper rowClusterMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RowClusterRepository rowClusterRepository;

  private final RowRepository rowRepository;

  private final EntityManager entityManager;

  @Override
  public List<RowClusterResponseDto> getAllRowClusters() {
    return rowClusterRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toRowClusterResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public List<RowResponseDto> getAllRowsByRowClusterId(Long rowClusterId) {
    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    val rows = rowRepository.findAllByRowClusterOrderByOrdinalAsc(getById(rowClusterId)).stream()
      .map(toRowResponseDtoConverter::convert)
      .toList();

    session.disableFilter("isNotDeleted");

    return rows;
  }

  @Override
  public FullRowClusterResponseDto getRowClusterById(Long id) {
    return toFullRowClusterResponseDtoConverter.convert(getById(id));

  }

  @Override
  @Transactional
  public FullRowClusterResponseDto createRowCluster(CreateRowClusterRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRowClusterRequestDtoConverter.convert(requestDto));

    joinedCreateRowClusterRequestDtoValidator.validate(joinedRequestDto);

    val rowCluster = rowClusterRepository.save(Objects.requireNonNull(fromJoinedCreateRowClusterRequestDtoConverter.convert(joinedRequestDto)));

    return toFullRowClusterResponseDtoConverter.convert(rowCluster);
  }

  @Override
  @Transactional
  public FullRowClusterResponseDto updateRowClusterById(Long id, UpdateRowClusterRequestDto requestDto) {
    val rowCluster = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateRowClusterRequestDtoConverter.convert(requestDto));

    rowClusterWithJoinedUpdateRowClusterRequestDtoValidator.validate(rowCluster, joinedRequestDto);

    return toFullRowClusterResponseDtoConverter.convert(
      rowClusterRepository.save(
        rowClusterMapper.partialUpdate(rowCluster, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteRowClusterById(Long id) {
    rowClusterRepository.delete(getById(id));
  }

  @Override
  public RowCluster getById(Long id) {
    return rowClusterRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Tabla"));
  }

}
