package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.ArcodeParcelToArcodeParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CadastralParcelToCadastralParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CadastralParcelToFullCadastralParcelResponseDtoConverter;
import hr.algebra.fruity.converter.CreateCadastralParcelRequestDtoToJoinedCreateCadastralParcelRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateCadastralParcelRequestDtoToCadastralParcelConverter;
import hr.algebra.fruity.converter.UpdateCadastralParcelRequestDtoToJoinedUpdateCadastralParcelRequestDtoConverter;
import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.CadastralParcelMapper;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.CadastralParcelService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.CadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateCadastralParcelRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserCadastralParcelService implements CadastralParcelService {

  private final CadastralParcelToCadastralParcelResponseDtoConverter toCadastralParcelResponseDtoConverter;

  private final CadastralParcelToFullCadastralParcelResponseDtoConverter toFullCadastralParcelResponseDtoConverter;

  private final CreateCadastralParcelRequestDtoToJoinedCreateCadastralParcelRequestDtoConverter toJoinedCreateCadastralParcelRequestDtoConverter;

  private final JoinedCreateCadastralParcelRequestDtoToCadastralParcelConverter fromJoinedCreateCadastralParcelRequestDtoConverter;

  private final ArcodeParcelToArcodeParcelResponseDtoConverter toArcodeParcelResponseDtoConverter;

  private final JoinedCreateCadastralParcelRequestDtoValidator joinedCreateCadastralParcelRequestDtoValidator;

  private final UpdateCadastralParcelRequestDtoToJoinedUpdateCadastralParcelRequestDtoConverter toJoinedUpdateCadastralParcelRequestDtoConverter;

  private final CadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator cadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator;

  private final CadastralParcelMapper cadastralParcelMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final CadastralParcelRepository cadastralParcelRepository;

  private final ArcodeParcelRepository arcodeParcelRepository;

  @Override
  public List<CadastralParcelResponseDto> getAllCadastralParcels() {
    return cadastralParcelRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toCadastralParcelResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public List<ArcodeParcelResponseDto> getAllArcodeParcelsByCadastralParcelId(Long cadastralParcelFk) {
    return arcodeParcelRepository.findAllByCadastralParcel(getById(cadastralParcelFk)).stream()
      .map(toArcodeParcelResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullCadastralParcelResponseDto getCadastralParcelById(Long id) {
    return toFullCadastralParcelResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public FullCadastralParcelResponseDto createCadastralParcel(CreateCadastralParcelRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateCadastralParcelRequestDtoConverter.convert(requestDto));

    joinedCreateCadastralParcelRequestDtoValidator.validate(joinedRequestDto);

    val cadastralParcel = cadastralParcelRepository.save(Objects.requireNonNull(fromJoinedCreateCadastralParcelRequestDtoConverter.convert(joinedRequestDto)));

    return toFullCadastralParcelResponseDtoConverter.convert(cadastralParcel);
  }

  @Override
  @Transactional
  public FullCadastralParcelResponseDto updateCadastralParcelById(Long id, UpdateCadastralParcelRequestDto requestDto) {
    val cadastralParcel = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateCadastralParcelRequestDtoConverter.convert(requestDto));

    cadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator.validate(cadastralParcel, joinedRequestDto);

    return toFullCadastralParcelResponseDtoConverter.convert(
      cadastralParcelRepository.save(
        cadastralParcelMapper.partialUpdate(cadastralParcel, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteCadastralParcelById(Long id) {
    cadastralParcelRepository.delete(getById(id));
  }

  @Override
  public CadastralParcel getById(Long id) {
    return cadastralParcelRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Katastarska čestica"));
  }


}
