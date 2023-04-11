package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.CadastralParcelMapper;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.CadastralParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.CreateCadastralParcelRequestDtoValidator;
import hr.algebra.fruity.validator.CadastralParcelWithUpdateCadastralParcelRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserCadastralParcelService implements CadastralParcelService {

  private final ConversionService conversionService;

  private final CreateCadastralParcelRequestDtoValidator createCadastralParcelRequestDtoValidator;

  private final CadastralParcelWithUpdateCadastralParcelRequestDtoValidator cadastralParcelWithUpdateCadastralParcelRequestDtoValidator;

  private final CadastralParcelMapper cadastralParcelMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final CadastralParcelRepository cadastralParcelRepository;

  @Override
  public List<CadastralParcelResponseDto> getAllCadastralParcels() {
    return cadastralParcelRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(cadastralParcel -> conversionService.convert(cadastralParcel, CadastralParcelResponseDto.class))
      .toList();
  }

  @Override
  public FullCadastralParcelResponseDto getCadastralParcelById(Long id) {
    return conversionService.convert(getCadastralParcel(id), FullCadastralParcelResponseDto.class);
  }

  @Override
  @Transactional
  public FullCadastralParcelResponseDto createCadastralParcel(CreateCadastralParcelRequestDto requestDto) {
    createCadastralParcelRequestDtoValidator.validate(requestDto);

    val cadastralParcel = cadastralParcelRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, CadastralParcel.class)));

    return conversionService.convert(cadastralParcel, FullCadastralParcelResponseDto.class);
  }

  @Override
  @Transactional
  public FullCadastralParcelResponseDto updateCadastralParcelById(Long id, UpdateCadastralParcelRequestDto requestDto) {
    val cadastralParcel = getCadastralParcel(id);

    cadastralParcelWithUpdateCadastralParcelRequestDtoValidator.validate(cadastralParcel, requestDto);

    return conversionService.convert(
      cadastralParcelRepository.save(
        cadastralParcelMapper.partialUpdate(cadastralParcel, requestDto)
      ),
      FullCadastralParcelResponseDto.class
    );
  }

  @Override
  @Transactional
  public void deleteCadastralParcelById(Long id) {
    cadastralParcelRepository.delete(getCadastralParcel(id));
  }

  private CadastralParcel getCadastralParcel(Long id) {
    val cadastralParcel = cadastralParcelRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(cadastralParcel.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return cadastralParcel;
  }


}
