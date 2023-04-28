package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRealisationRequestDtoToJoinedCreateRealisationRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationRequestDtoToRealisationConverter;
import hr.algebra.fruity.converter.RealisationToFullRealisationResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationToRealisationResponseDtoConverter;
import hr.algebra.fruity.converter.UpdateRealisationRequestDtoToJoinedUpdateRealisationRequestDtoConverter;
import hr.algebra.fruity.dto.request.CreateRealisationRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationMapper;
import hr.algebra.fruity.model.Realisation;
import hr.algebra.fruity.repository.RealisationRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RealisationService;
import hr.algebra.fruity.validator.JoinedCreateRealisationRequestDtoValidator;
import hr.algebra.fruity.validator.RealisationWithJoinedUpdateRealisationRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRealisationService implements RealisationService {

  private final RealisationToRealisationResponseDtoConverter toRealisationResponseDtoConverter;

  private final RealisationToFullRealisationResponseDtoConverter toFullRealisationResponseDtoConverter;

  private final CreateRealisationRequestDtoToJoinedCreateRealisationRequestDtoConverter toJoinedCreateRealisationRequestDtoConverter;

  private final JoinedCreateRealisationRequestDtoToRealisationConverter fromJoinedCreateRealisationRequestDtoConverter;

  private final UpdateRealisationRequestDtoToJoinedUpdateRealisationRequestDtoConverter toJoinedUpdateRealisationRequestDtoConverter;

  private final JoinedCreateRealisationRequestDtoValidator joinedCreateRealisationRequestDtoValidator;

  private final RealisationWithJoinedUpdateRealisationRequestDtoValidator realisationWithJoinedUpdateRealisationRequestDtoValidator;

  private final RealisationMapper realisationMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RealisationRepository realisationRepository;

  @Override
  public List<RealisationResponseDto> getAllRealisations() {
    return realisationRepository.findAllByWorkUserId(currentRequestUserService.getUserId()).stream()
      .map(toRealisationResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRealisationResponseDto getRealisationById(Long id) {
    return toFullRealisationResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public FullRealisationResponseDto createRealisation(CreateRealisationRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRealisationRequestDtoConverter.convert(requestDto));

    joinedCreateRealisationRequestDtoValidator.validate(joinedRequestDto);

    val realisation = realisationRepository.save(Objects.requireNonNull(fromJoinedCreateRealisationRequestDtoConverter.convert(joinedRequestDto)));

    return toFullRealisationResponseDtoConverter.convert(realisation);
  }

  @Override
  @Transactional
  public FullRealisationResponseDto updateRealisationById(Long id, UpdateRealisationRequestDto requestDto) {
    val realisation = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateRealisationRequestDtoConverter.convert(requestDto));

    realisationWithJoinedUpdateRealisationRequestDtoValidator.validate(realisation, joinedRequestDto);

    return toFullRealisationResponseDtoConverter.convert(
      realisationRepository.save(
        realisationMapper.partialUpdate(realisation, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteRealisationById(Long id) {
    realisationRepository.delete(getById(id));
  }

  @Override
  public Realisation getById(Long id) {
    return realisationRepository.findByIdAndWorkUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Realizacija"));
  }

}
