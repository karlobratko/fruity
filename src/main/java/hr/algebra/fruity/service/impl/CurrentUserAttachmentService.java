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
import hr.algebra.fruity.repository.RealisationAttachmentRepository;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.service.AttachmentService;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.validator.AttachmentWithUpdateAttachmentRequestDtoValidator;
import hr.algebra.fruity.validator.CreateAttachmentRequestDtoValidator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Session;
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

  private final WorkAttachmentRepository workAttachmentRepository;

  private final RealisationAttachmentRepository realisationAttachmentRepository;

  private final EntityManager entityManager;

  @Override
  public List<AttachmentResponseDto> getAllAttachments() {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val attachments = attachmentRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toAttachmentResponseDtoConverter::convert)
      .toList();

    session.disableFilter("isNotDeleted");

    return attachments;
  }

  @Override
  public AttachmentResponseDto getAttachmentById(Long id) {
    return toAttachmentResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public AttachmentResponseDto createAttachment(CreateAttachmentRequestDto requestDto) {
    createAttachmentRequestDtoValidator.validate(requestDto);

    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val attachment = attachmentRepository.save(Objects.requireNonNull(fromCreateAttachmentRequestDtoConverter.convert(requestDto)));

    session.disableFilter("isNotDeleted");

    return toAttachmentResponseDtoConverter.convert(attachment);
  }

  @Override
  @Transactional
  public AttachmentResponseDto updateAttachmentById(Long id, UpdateAttachmentRequestDto requestDto) {
    val attachment = getById(id);

    attachmentWithUpdateAttachmentRequestDtoValidator.validate(attachment, requestDto);

    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    val savedAttachment = attachmentRepository.save(attachmentMapper.partialUpdate(attachment, requestDto));

    session.disableFilter("isNotDeleted");

    return toAttachmentResponseDtoConverter.convert(savedAttachment);
  }

  @Override
  @Transactional
  public void deleteAttachmentById(Long id) {
    val attachment = getById(id);

    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    workAttachmentRepository.deleteByAttachmentAndWorkFinishedFalse(attachment);
    realisationAttachmentRepository.deleteByAttachmentAndRealisationWorkFinishedFalse(attachment);

    attachmentRepository.delete(attachment);

    session.disableFilter("isNotDeleted");
  }

  @Override
  public Attachment getById(Long id) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val attachment = attachmentRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Priključak"));

    session.disableFilter("isNotDeleted");

    return attachment;
  }

  @Override
  public Attachment getByIdIgnoreSoftDelete(Long id) {
    return attachmentRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Priključak"));
  }

  @Override
  public List<Attachment> getAllById(List<Long> ids) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val attachments = attachmentRepository.findAllByIdsAndUserId(ids, currentRequestUserService.getUserId());

    session.disableFilter("isNotDeleted");

    return attachments;
  }

}
