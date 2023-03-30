package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.AttachmentMapper;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.service.AttachmentService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.AttachmentWithUpdateAttachmentRequestDtoValidator;
import hr.algebra.fruity.validator.CreateAttachmentRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserAttachmentService implements AttachmentService {

  private final ConversionService conversionService;

  private final CreateAttachmentRequestDtoValidator createAttachmentRequestDtoValidator;

  private final AttachmentWithUpdateAttachmentRequestDtoValidator attachmentWithUpdateAttachmentRequestDtoValidator;

  private final AttachmentMapper attachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final AttachmentRepository attachmentRepository;

  @Override
  public List<AttachmentResponseDto> getAllAttachments() {
    return attachmentRepository.findAllByUser(currentRequestUserService.getUser()).stream()
      .map(equipment -> conversionService.convert(equipment, AttachmentResponseDto.class))
      .toList();
  }

  @Override
  public AttachmentResponseDto getAttachmentById(Long id) {
    return conversionService.convert(getAttachment(id), AttachmentResponseDto.class);
  }

  @Override
  public AttachmentResponseDto createAttachment(CreateAttachmentRequestDto requestDto) {
    createAttachmentRequestDtoValidator.validate(requestDto);

    val attachment = attachmentRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, Attachment.class)));

    return conversionService.convert(attachment, AttachmentResponseDto.class);
  }

  @Override
  public AttachmentResponseDto updateAttachmentById(Long id, UpdateAttachmentRequestDto requestDto) {
    val attachment = getAttachment(id);

    attachmentWithUpdateAttachmentRequestDtoValidator.validate(attachment, requestDto);

    return conversionService.convert(
      attachmentRepository.save(
        attachmentMapper.partialUpdate(attachment, requestDto)
      ),
      AttachmentResponseDto.class
    );
  }

  @Override
  public void deleteAttachmentById(Long id) {
    attachmentRepository.delete(getAttachment(id));
  }

  private Attachment getAttachment(Long id) {
    val attachment = attachmentRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(attachment.getUser(), currentRequestUserService.getUser()))
      throw new ForeignUserDataAccessException();

    return attachment;
  }

}
