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
import hr.algebra.fruity.service.EquipmentService;
import hr.algebra.fruity.validator.AttachmentWithUpdateAttachmentRequestDtoValidator;
import hr.algebra.fruity.validator.CreateAttachmentRequestDtoValidator;
import jakarta.transaction.Transactional;
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

  private final EquipmentService equipmentService;

  @Override
  public List<AttachmentResponseDto> getAllAttachments() {
    return attachmentRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(equipment -> conversionService.convert(equipment, AttachmentResponseDto.class))
      .toList();
  }

  @Override
  public List<AttachmentResponseDto> getAllAttachmentsByEquipmentId(Long equipmentFk) {
    return attachmentRepository.findAllByEquipment(equipmentService.getById(equipmentFk)).stream()
      .map(equipment -> conversionService.convert(equipment, AttachmentResponseDto.class))
      .toList();
  }

  @Override
  public AttachmentResponseDto getAttachmentById(Long id) {
    return conversionService.convert(getById(id), AttachmentResponseDto.class);
  }

  @Override
  @Transactional
  public AttachmentResponseDto createAttachment(CreateAttachmentRequestDto requestDto) {
    createAttachmentRequestDtoValidator.validate(requestDto);

    val attachment = attachmentRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, Attachment.class)));

    return conversionService.convert(attachment, AttachmentResponseDto.class);
  }

  @Override
  @Transactional
  public AttachmentResponseDto updateAttachmentById(Long id, UpdateAttachmentRequestDto requestDto) {
    val attachment = getById(id);

    attachmentWithUpdateAttachmentRequestDtoValidator.validate(attachment, requestDto);

    return conversionService.convert(
      attachmentRepository.save(
        attachmentMapper.partialUpdate(attachment, requestDto)
      ),
      AttachmentResponseDto.class
    );
  }

  @Override
  @Transactional
  public void deleteAttachmentById(Long id) {
    attachmentRepository.delete(getById(id));
  }

  @Override
  public Attachment getById(Long id) {
    val attachment = attachmentRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(attachment.getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return attachment;
  }

}
