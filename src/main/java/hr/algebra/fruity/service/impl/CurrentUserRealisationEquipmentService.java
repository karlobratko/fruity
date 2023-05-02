package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRealisationEquipmentRequestDtoToJoinedCreateRealisationEquipmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterToRealisationEquipmentConverter;
import hr.algebra.fruity.converter.RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationEquipmentToRealisationEquipmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.dto.response.FullRealisationEquipmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationEquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationEquipmentMapper;
import hr.algebra.fruity.model.RealisationEquipment;
import hr.algebra.fruity.repository.RealisationEquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RealisationEquipmentService;
import hr.algebra.fruity.service.RealisationService;
import hr.algebra.fruity.validator.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRealisationEquipmentService implements RealisationEquipmentService {

  private final RealisationEquipmentToRealisationEquipmentResponseDtoConverter toRealisationEquipmentResponseDtoConverter;

  private final RealisationEquipmentToFullRealisationEquipmentResponseDtoConverter toFullRealisationEquipmentResponseDtoConverter;

  private final CreateRealisationEquipmentRequestDtoToJoinedCreateRealisationEquipmentRequestDtoConverter toJoinedCreateRealisationEquipmentRequestDtoConverter;

  private final JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterToRealisationEquipmentConverter fromJoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterConverter;

  private final JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator joinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator;

  private final RealisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator realisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator;

  private final RealisationEquipmentMapper realisationEquipmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RealisationEquipmentRepository realisationEquipmentRepository;

  private final RealisationService realisationService;

  @Override
  public List<RealisationEquipmentResponseDto> getAllRealisationEquipmentByRealisationId(Long realisationFk) {
    return realisationEquipmentRepository.findAllByRealisation(realisationService.getById(realisationFk)).stream()
      .map(toRealisationEquipmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRealisationEquipmentResponseDto getRealisationEquipmentByRealisationIdAndEquipmentId(Long realisationFk, Long attachmentFk) {
    return toFullRealisationEquipmentResponseDtoConverter.convert(getByRealisationIdAndEquipmentId(realisationFk, attachmentFk));
  }

  @Override
  public FullRealisationEquipmentResponseDto createRealisationEquipmentForRealisationId(Long realisationFk, CreateRealisationEquipmentRequestDto requestDto) {
    val realisation = realisationService.getById(realisationFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRealisationEquipmentRequestDtoConverter.convert(requestDto));

    val requestDtoWithRealisation = new JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter(joinedRequestDto, realisation);
    joinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator.validate(requestDtoWithRealisation);

    val realisationEquipment = realisationEquipmentRepository.save(Objects.requireNonNull(fromJoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterConverter.convert(requestDtoWithRealisation)));
    return toFullRealisationEquipmentResponseDtoConverter.convert(realisationEquipment);
  }

  @Override
  public FullRealisationEquipmentResponseDto updateRealisationEquipmentByRealisationIdAndEquipmentId(Long realisationFk, Long attachmentFk, UpdateRealisationEquipmentRequestDto requestDto) {
    val realisationEquipment = getByRealisationIdAndEquipmentId(realisationFk, attachmentFk);

    realisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator.validate(realisationEquipment, requestDto);

    return toFullRealisationEquipmentResponseDtoConverter.convert(
      realisationEquipmentRepository.save(
        realisationEquipmentMapper.partialUpdate(realisationEquipment, requestDto)
      )
    );
  }

  @Override
  public void deleteRealisationEquipmentByRealisationIdAndEquipmentId(Long realisationFk, Long attachmentFk) {
    realisationEquipmentRepository.delete(getByRealisationIdAndEquipmentId(realisationFk, attachmentFk));
  }

  @Override
  public RealisationEquipment getByRealisationIdAndEquipmentId(Long realisationFk, Long attachmentFk) {
    return realisationEquipmentRepository.findByRealisationIdAndEquipmentIdAndRealisationWorkUserId(realisationFk, attachmentFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Oprema korištena u realizaciji"));
  }

}
