package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.request.UpdateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.response.FullWorkEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkEmployeeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkEmployeeMapper;
import hr.algebra.fruity.model.WorkEmployee;
import hr.algebra.fruity.repository.WorkEmployeeRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkEmployeeService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.CreateWorkEmployeeRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkEmployeeService implements WorkEmployeeService {

  private final ConversionService conversionService;

  private final CreateWorkEmployeeRequestDtoWithWorkAdapterValidator createWorkEmployeeRequestDtoWithWorkAdapterValidator;

  private final WorkEmployeeMapper workAttachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkEmployeeRepository workAttachmentRepository;

  private final WorkService workService;

  @Override
  public List<WorkEmployeeResponseDto> getAllWorkEmployeesByWorkId(Long workFk) {
    return workAttachmentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(workAttachment -> conversionService.convert(workAttachment, WorkEmployeeResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkEmployeeResponseDto getWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeFk) {
    return conversionService.convert(getByWorkIdAndEmployeeId(workFk, employeeFk), FullWorkEmployeeResponseDto.class);
  }

  @Override
  public FullWorkEmployeeResponseDto createWorkEmployeeForWorkId(Long workFk, CreateWorkEmployeeRequestDto requestDto) {
    val work = workService.getById(workFk);

    val requestDtoWithWork = new CreateWorkEmployeeRequestDtoWithWorkAdapter(requestDto, work);
    createWorkEmployeeRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workAttachment = workAttachmentRepository.save(Objects.requireNonNull(conversionService.convert(requestDtoWithWork, WorkEmployee.class)));
    return conversionService.convert(workAttachment, FullWorkEmployeeResponseDto.class);
  }

  @Override
  public FullWorkEmployeeResponseDto updateWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeFk, UpdateWorkEmployeeRequestDto requestDto) {
    val workAttachment = getByWorkIdAndEmployeeId(workFk, employeeFk);

    return conversionService.convert(
      workAttachmentRepository.save(
        workAttachmentMapper.partialUpdate(workAttachment, requestDto)
      ),
      FullWorkEmployeeResponseDto.class
    );
  }

  @Override
  public void deleteWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeFk) {
    workAttachmentRepository.delete(getByWorkIdAndEmployeeId(workFk, employeeFk));
  }

  @Override
  public WorkEmployee getByWorkIdAndEmployeeId(Long workFk, Long employeeFk) {
    val workAttachment = workAttachmentRepository.findByWorkIdAndEmployeeId(workFk, employeeFk)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(workAttachment.getWork().getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return workAttachment;
  }

}
