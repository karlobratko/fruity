package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRealisationAttachmentRequestDtoToJoinedCreateRealisationAttachmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter;
import hr.algebra.fruity.converter.RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationAttachmentToRealisationAttachmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.dto.response.FullRealisationAttachmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationAttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.mapper.RealisationAttachmentMapper;
import hr.algebra.fruity.model.RealisationAttachment;
import hr.algebra.fruity.repository.RealisationAttachmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.RealisationAttachmentService;
import hr.algebra.fruity.service.RealisationService;
import hr.algebra.fruity.validator.JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRealisationAttachmentService implements RealisationAttachmentService {

  private final RealisationAttachmentToRealisationAttachmentResponseDtoConverter toRealisationAttachmentResponseDtoConverter;

  private final RealisationAttachmentToFullRealisationAttachmentResponseDtoConverter toFullRealisationAttachmentResponseDtoConverter;

  private final CreateRealisationAttachmentRequestDtoToJoinedCreateRealisationAttachmentRequestDtoConverter toJoinedCreateRealisationAttachmentRequestDtoConverter;

  private final JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterToRealisationAttachmentConverter fromJoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterConverter;

  private final JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator joinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator;

  private final RealisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator realisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator;

  private final RealisationAttachmentMapper realisationAttachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RealisationAttachmentRepository realisationAttachmentRepository;

  private final RealisationService realisationService;

  @Override
  public List<RealisationAttachmentResponseDto> getAllRealisationAttachmentsByRealisationId(Long realisationFk) {
    return realisationAttachmentRepository.findAllByRealisation(realisationService.getById(realisationFk)).stream()
      .map(toRealisationAttachmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRealisationAttachmentResponseDto getRealisationAttachmentByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk) {
    return toFullRealisationAttachmentResponseDtoConverter.convert(getByRealisationIdAndAttachmentId(realisationFk, attachmentFk));
  }

  @Override
  public FullRealisationAttachmentResponseDto createRealisationAttachmentForRealisationId(Long realisationFk, CreateRealisationAttachmentRequestDto requestDto) {
    val realisation = realisationService.getById(realisationFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRealisationAttachmentRequestDtoConverter.convert(requestDto));

    val requestDtoWithRealisation = new JoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapter(joinedRequestDto, realisation);
    joinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterValidator.validate(requestDtoWithRealisation);

    val realisationAttachment = realisationAttachmentRepository.save(Objects.requireNonNull(fromJoinedCreateRealisationAttachmentRequestDtoWithRealisationAdapterConverter.convert(requestDtoWithRealisation)));
    return toFullRealisationAttachmentResponseDtoConverter.convert(realisationAttachment);
  }

  @Override
  public FullRealisationAttachmentResponseDto updateRealisationAttachmentByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk, UpdateRealisationAttachmentRequestDto requestDto) {
    val realisationAttachment = getByRealisationIdAndAttachmentId(realisationFk, attachmentFk);

    realisationAttachmentWithUpdateRealisationAttachmentRequestDtoValidator.validate(realisationAttachment, requestDto);

    return toFullRealisationAttachmentResponseDtoConverter.convert(
      realisationAttachmentRepository.save(
        realisationAttachmentMapper.partialUpdate(realisationAttachment, requestDto)
      )
    );
  }

  @Override
  public void deleteRealisationAttachmentByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk) {
    val realisationAttachment = getByRealisationIdAndAttachmentId(realisationFk, attachmentFk);

    if (realisationAttachment.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    realisationAttachmentRepository.delete(realisationAttachment);
  }

  @Override
  public RealisationAttachment getByRealisationIdAndAttachmentId(Long realisationFk, Long attachmentFk) {
    return realisationAttachmentRepository.findByRealisationIdAndAttachmentIdAndRealisationWorkUserId(realisationFk, attachmentFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Priključak korišten u realizaciji"));
  }

}
