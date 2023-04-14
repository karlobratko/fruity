package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.RowClusterMapper;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.service.ArcodeParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RowClusterService;
import hr.algebra.fruity.validator.CreateRowClusterRequestDtoValidator;
import hr.algebra.fruity.validator.RowClusterWithUpdateRowClusterRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRowClusterService implements RowClusterService {

  private final ConversionService conversionService;

  private final CreateRowClusterRequestDtoValidator createRowClusterRequestDtoValidator;

  private final RowClusterWithUpdateRowClusterRequestDtoValidator rowClusterWithUpdateRowClusterRequestDtoValidator;

  private final RowClusterMapper rowClusterMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RowClusterRepository rowClusterRepository;

  private final ArcodeParcelService arcodeParcelService;

  @Override
  public List<RowClusterResponseDto> getAllRowClusters() {
    return rowClusterRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(rowCluster -> conversionService.convert(rowCluster, RowClusterResponseDto.class))
      .toList();
  }

  @Override
  public List<RowClusterResponseDto> getAllRowClustersByArcodeParcelId(Long arcodeParcelId) {
    return rowClusterRepository.findAllByArcodeParcel(arcodeParcelService.getById(arcodeParcelId)).stream()
      .map(rowCluster -> conversionService.convert(rowCluster, RowClusterResponseDto.class))
      .toList();
  }

  @Override
  public FullRowClusterResponseDto getRowClusterById(Long id) {
    return conversionService.convert(getById(id), FullRowClusterResponseDto.class);

  }

  @Override
  public FullRowClusterResponseDto createRowCluster(CreateRowClusterRequestDto requestDto) {
    createRowClusterRequestDtoValidator.validate(requestDto);

    val rowCluster = rowClusterRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, RowCluster.class)));

    return conversionService.convert(rowCluster, FullRowClusterResponseDto.class);
  }

  @Override
  public FullRowClusterResponseDto updateRowClusterById(Long id, UpdateRowClusterRequestDto requestDto) {
    val rowCluster = getById(id);

    rowClusterWithUpdateRowClusterRequestDtoValidator.validate(rowCluster, requestDto);

    return conversionService.convert(
      rowClusterRepository.save(
        rowClusterMapper.partialUpdate(rowCluster, requestDto)
      ),
      FullRowClusterResponseDto.class
    );
  }

  @Override
  public void deleteRowClusterById(Long id) {
    rowClusterRepository.delete(getById(id));
  }

  @Override
  public RowCluster getById(Long id) {
    val rowCluster = rowClusterRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(rowCluster.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return rowCluster;
  }

}
