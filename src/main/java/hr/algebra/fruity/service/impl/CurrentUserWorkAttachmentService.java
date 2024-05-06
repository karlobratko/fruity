package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter;
import hr.algebra.fruity.converter.WorkAttachmentToFullWorkAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.WorkAttachmentToWorkAttachmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.dto.response.WorkAttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkAttachmentMapper;
import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkAttachmentService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkAttachmentService implements WorkAttachmentService {

  private final WorkAttachmentToWorkAttachmentResponseDtoConverter toWorkAttachmentResponseDtoConverter;

  private final WorkAttachmentToFullWorkAttachmentResponseDtoConverter toFullWorkAttachmentResponseDtoConverter;

  private final CreateWorkAttachmentRequestDtoToJoinedCreateWorkAttachmentRequestDtoConverter toJoinedCreateWorkAttachmentRequestDtoConverter;

  private final JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterToWorkAttachmentConverter fromJoinedCreateWorkAttachmentRequestDtoWithWorkAdapterConverter;

  private final JoinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator joinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator;

  private final WorkAttachmentMapper workAttachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkAttachmentRepository workAttachmentRepository;

  private final WorkService workService;

  @Override
  public List<WorkAttachmentResponseDto> getAllWorkAttachmentsByWorkId(Long workFk) {
    return workAttachmentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(toWorkAttachmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkAttachmentResponseDto getWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk) {
    return toFullWorkAttachmentResponseDtoConverter.convert(getByWorkIdAndAttachmentId(workFk, attachmentFk));
  }

  @Override
  public FullWorkAttachmentResponseDto createWorkAttachmentForWorkId(Long workFk, CreateWorkAttachmentRequestDto requestDto) {
    val work = workService.getById(workFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateWorkAttachmentRequestDtoConverter.convert(requestDto));

    val requestDtoWithWork = new JoinedCreateWorkAttachmentRequestDtoWithWorkAdapter(joinedRequestDto, work);
    joinedCreateWorkAttachmentRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workAttachment = workAttachmentRepository.save(Objects.requireNonNull(fromJoinedCreateWorkAttachmentRequestDtoWithWorkAdapterConverter.convert(requestDtoWithWork)));
    return toFullWorkAttachmentResponseDtoConverter.convert(workAttachment);
  }

  @Override
  public FullWorkAttachmentResponseDto updateWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk, UpdateWorkAttachmentRequestDto requestDto) {
    val workAttachment = getByWorkIdAndAttachmentId(workFk, attachmentFk);

    return toFullWorkAttachmentResponseDtoConverter.convert(
      workAttachmentRepository.save(
        workAttachmentMapper.partialUpdate(workAttachment, requestDto)
      )
    );
  }

  @Override
  public void deleteWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk) {
    workAttachmentRepository.delete(getByWorkIdAndAttachmentId(workFk, attachmentFk));
  }

  @Override
  public WorkAttachment getByWorkIdAndAttachmentId(Long workFk, Long attachmentFk) {
    return workAttachmentRepository.findByWorkIdAndAttachmentIdAndWorkUserId(workFk, attachmentFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Priključak korišten u radu"));
  }

}
