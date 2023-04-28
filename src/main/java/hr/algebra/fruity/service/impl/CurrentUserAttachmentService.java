package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.AttachmentToAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.CreateAttachmentRequestDtoToAttachmentConverter;
import hr.algebra.fruity.dto.request.CreateAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.AttachmentMapper;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.service.AttachmentService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.AttachmentWithUpdateAttachmentRequestDtoValidator;
import hr.algebra.fruity.validator.CreateAttachmentRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserAttachmentService implements AttachmentService {

  private final AttachmentToAttachmentResponseDtoConverter toAttachmentResponseDtoConverter;

  private final CreateAttachmentRequestDtoToAttachmentConverter fromCreateAttachmentRequestDtoConverter;

  private final CreateAttachmentRequestDtoValidator createAttachmentRequestDtoValidator;

  private final AttachmentWithUpdateAttachmentRequestDtoValidator attachmentWithUpdateAttachmentRequestDtoValidator;

  private final AttachmentMapper attachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final AttachmentRepository attachmentRepository;

  @Override
  public List<AttachmentResponseDto> getAllAttachments() {
    return attachmentRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toAttachmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public AttachmentResponseDto getAttachmentById(Long id) {
    return toAttachmentResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public AttachmentResponseDto createAttachment(CreateAttachmentRequestDto requestDto) {
    createAttachmentRequestDtoValidator.validate(requestDto);

    val attachment = attachmentRepository.save(Objects.requireNonNull(fromCreateAttachmentRequestDtoConverter.convert(requestDto)));

    return toAttachmentResponseDtoConverter.convert(attachment);
  }

  @Override
  @Transactional
  public AttachmentResponseDto updateAttachmentById(Long id, UpdateAttachmentRequestDto requestDto) {
    val attachment = getById(id);

    attachmentWithUpdateAttachmentRequestDtoValidator.validate(attachment, requestDto);

    return toAttachmentResponseDtoConverter.convert(
      attachmentRepository.save(
        attachmentMapper.partialUpdate(attachment, requestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteAttachmentById(Long id) {
    attachmentRepository.delete(getById(id));
  }

  @Override
  public Attachment getById(Long id) {
    return attachmentRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Priključak"));
  }

  @Override
  public List<Attachment> getAllById(List<Long> ids) {
    return attachmentRepository.findAllByIdsAndUserId(ids, currentRequestUserService.getUserId());
  }

}
