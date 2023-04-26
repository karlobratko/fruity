package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.request.UpdateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.dto.response.WorkAttachmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkAttachmentMapper;
import hr.algebra.fruity.model.WorkAttachment;
import hr.algebra.fruity.repository.WorkAttachmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkAttachmentService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.CreateWorkAttachmentRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkAttachmentService implements WorkAttachmentService {

  private final ConversionService conversionService;

  private final CreateWorkAttachmentRequestDtoWithWorkAdapterValidator createWorkAttachmentRequestDtoWithWorkAdapterValidator;

  private final WorkAttachmentMapper workAttachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkAttachmentRepository workAttachmentRepository;

  private final WorkService workService;

  @Override
  public List<WorkAttachmentResponseDto> getAllWorkAttachmentsByWorkId(Long workFk) {
    return workAttachmentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(workAttachment -> conversionService.convert(workAttachment, WorkAttachmentResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkAttachmentResponseDto getWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk) {
    return conversionService.convert(getByWorkIdAndAttachmentId(workFk, attachmentFk), FullWorkAttachmentResponseDto.class);
  }

  @Override
  public FullWorkAttachmentResponseDto createWorkAttachmentForWorkId(Long workFk, CreateWorkAttachmentRequestDto requestDto) {
    val work = workService.getById(workFk);

    val requestDtoWithWork = new CreateWorkAttachmentRequestDtoWithWorkAdapter(requestDto, work);
    createWorkAttachmentRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workAttachment = workAttachmentRepository.save(Objects.requireNonNull(conversionService.convert(requestDtoWithWork, WorkAttachment.class)));
    return conversionService.convert(workAttachment, FullWorkAttachmentResponseDto.class);
  }

  @Override
  public FullWorkAttachmentResponseDto updateWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk, UpdateWorkAttachmentRequestDto requestDto) {
    val workAttachment = getByWorkIdAndAttachmentId(workFk, attachmentFk);

    return conversionService.convert(
      workAttachmentRepository.save(
        workAttachmentMapper.partialUpdate(workAttachment, requestDto)
      ),
      FullWorkAttachmentResponseDto.class
    );
  }

  @Override
  public void deleteWorkAttachmentByWorkIdAndAttachmentId(Long workFk, Long attachmentFk) {
    workAttachmentRepository.delete(getByWorkIdAndAttachmentId(workFk, attachmentFk));
  }

  @Override
  public WorkAttachment getByWorkIdAndAttachmentId(Long workFk, Long attachmentFk) {
    val workAttachment = workAttachmentRepository.findByWorkIdAndAttachmentId(workFk, attachmentFk)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(workAttachment.getWork().getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return workAttachment;
  }

}
