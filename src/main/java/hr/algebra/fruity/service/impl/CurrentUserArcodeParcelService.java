package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.ArcodeParcelToArcodeParcelResponseDtoConverter;
import hr.algebra.fruity.converter.ArcodeParcelToFullArcodeParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CreateArcodeParcelRequestDtoToJoinedCreateArcodeParcelRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateArcodeParcelRequestDtoToArcodeParcelConverter;
import hr.algebra.fruity.converter.RowClusterToRowClusterResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateArcodeParcelRequestDtoToJoinedUpdateArcodeParcelRequestDtoConverter;
import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.ArcodeParcelMapper;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.repository.RowClusterRepository;
import hr.algebra.fruity.service.ArcodeParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.ArcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateArcodeParcelRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserArcodeParcelService implements ArcodeParcelService {

  private final ArcodeParcelToArcodeParcelResponseDtoConverter toArcodeParcelResponseDtoConverter;

  private final ArcodeParcelToFullArcodeParcelResponseDtoConverter toFullArcodeParcelResponseDtoConverter;

  private final CreateArcodeParcelRequestDtoToJoinedCreateArcodeParcelRequestDtoConverter toJoinedCreateArcodeParcelRequestDtoConverter;

  private final JoinedCreateArcodeParcelRequestDtoToArcodeParcelConverter fromJoinedCreateArcodeParcelRequestDtoConverter;

  private final UpdateArcodeParcelRequestDtoToJoinedUpdateArcodeParcelRequestDtoConverter toJoinedUpdateArcodeParcelRequestDtoConverter;

  private final RowClusterToRowClusterResponseDtoConverter toRowClusterResponseDtoConverter;

  private final JoinedCreateArcodeParcelRequestDtoValidator joinedCreateArcodeParcelRequestDtoValidator;

  private final ArcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator arcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator;

  private final ArcodeParcelMapper arcodeParcelMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final ArcodeParcelRepository arcodeParcelRepository;

  private final RowClusterRepository rowClusterRepository;

  @Override
  public List<ArcodeParcelResponseDto> getAllArcodeParcels() {
    return arcodeParcelRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toArcodeParcelResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public List<RowClusterResponseDto> getAllRowClustersByArcodeParcelId(Long arcodeParcelId) {
    return rowClusterRepository.findAllByArcodeParcel(getById(arcodeParcelId)).stream()
      .map(toRowClusterResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullArcodeParcelResponseDto getArcodeParcelById(Long id) {
    return toFullArcodeParcelResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public FullArcodeParcelResponseDto createArcodeParcel(CreateArcodeParcelRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateArcodeParcelRequestDtoConverter.convert(requestDto));

    joinedCreateArcodeParcelRequestDtoValidator.validate(joinedRequestDto);

    val arcodeParcel = arcodeParcelRepository.save(Objects.requireNonNull(fromJoinedCreateArcodeParcelRequestDtoConverter.convert(joinedRequestDto)));

    return toFullArcodeParcelResponseDtoConverter.convert(arcodeParcel);
  }

  @Override
  @Transactional
  public FullArcodeParcelResponseDto updateArcodeParcelById(Long id, UpdateArcodeParcelRequestDto requestDto) {
    val arcodeParcel = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateArcodeParcelRequestDtoConverter.convert(requestDto));

    arcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator.validate(arcodeParcel, joinedRequestDto);

    return toFullArcodeParcelResponseDtoConverter.convert(
      arcodeParcelRepository.save(
        arcodeParcelMapper.partialUpdate(arcodeParcel, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteArcodeParcelById(Long id) {
    arcodeParcelRepository.delete(getById(id));
  }

  @Override
  public ArcodeParcel getById(Long id) {
    return arcodeParcelRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("ARKOD parcela"));
  }

}
