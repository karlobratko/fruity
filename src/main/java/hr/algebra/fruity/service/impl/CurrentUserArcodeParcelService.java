package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.ArcodeParcelMapper;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.service.ArcodeParcelService;
import hr.algebra.fruity.service.CadastralParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.ArcodeParcelWithUpdateArcodeParcelRequestDtoValidator;
import hr.algebra.fruity.validator.CreateArcodeParcelRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserArcodeParcelService implements ArcodeParcelService {

  private final ConversionService conversionService;

  private final CreateArcodeParcelRequestDtoValidator createArcodeParcelRequestDtoValidator;

  private final ArcodeParcelWithUpdateArcodeParcelRequestDtoValidator arcodeParcelWithUpdateArcodeParcelRequestDtoValidator;

  private final ArcodeParcelMapper arcodeParcelMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final ArcodeParcelRepository arcodeParcelRepository;

  private final CadastralParcelService cadastralParcelService;

  @Override
  public List<ArcodeParcelResponseDto> getAllArcodeParcels() {
    return arcodeParcelRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(arcodeParcel -> conversionService.convert(arcodeParcel, ArcodeParcelResponseDto.class))
      .toList();
  }

  @Override
  public List<ArcodeParcelResponseDto> getAllArcodeParcelsByCadastralParcelId(Long cadastralParcelFk) {
    return arcodeParcelRepository.findAllByCadastralParcel(cadastralParcelService.getById(cadastralParcelFk)).stream()
      .map(arcodeParcel -> conversionService.convert(arcodeParcel, ArcodeParcelResponseDto.class))
      .toList();
  }

  @Override
  public FullArcodeParcelResponseDto getArcodeParcelById(Long id) {
    return conversionService.convert(getById(id), FullArcodeParcelResponseDto.class);
  }

  @Override
  @Transactional
  public FullArcodeParcelResponseDto createArcodeParcel(CreateArcodeParcelRequestDto requestDto) {
    createArcodeParcelRequestDtoValidator.validate(requestDto);

    val arcodeParcel = arcodeParcelRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, ArcodeParcel.class)));

    return conversionService.convert(arcodeParcel, FullArcodeParcelResponseDto.class);
  }

  @Override
  @Transactional
  public FullArcodeParcelResponseDto updateArcodeParcelById(Long id, UpdateArcodeParcelRequestDto requestDto) {
    val arcodeParcel = getById(id);

    arcodeParcelWithUpdateArcodeParcelRequestDtoValidator.validate(arcodeParcel, requestDto);

    return conversionService.convert(
      arcodeParcelRepository.save(
        arcodeParcelMapper.partialUpdate(arcodeParcel, requestDto)
      ),
      FullArcodeParcelResponseDto.class
    );
  }

  @Override
  @Transactional
  public void deleteArcodeParcelById(Long id) {
    arcodeParcelRepository.delete(getById(id));
  }

  @Override
  public ArcodeParcel getById(Long id) {
    val arcodeParcel = arcodeParcelRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(arcodeParcel.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return arcodeParcel;
  }

}
