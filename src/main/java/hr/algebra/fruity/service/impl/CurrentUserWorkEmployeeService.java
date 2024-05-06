package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter;
import hr.algebra.fruity.converter.WorkEmployeeToFullWorkEmployeeResponseDtoConverter;
import hr.algebra.fruity.converter.WorkEmployeeToWorkEmployeeResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkEmployeeResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkEmployeeMapper;
import hr.algebra.fruity.model.WorkEmployee;
import hr.algebra.fruity.repository.WorkEmployeeRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkEmployeeService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkEmployeeService implements WorkEmployeeService {

  private final WorkEmployeeToWorkEmployeeResponseDtoConverter toWorkEmployeeResponseDtoConverter;

  private final WorkEmployeeToFullWorkEmployeeResponseDtoConverter toFullWorkEmployeeResponseDtoConverter;

  private final CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter toJoinedCreateWorkEmployeeRequestDtoConverter;

  private final JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter fromJoinedCreateWorkEmployeeRequestDtoWithWorkAdapterConverter;

  private final JoinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator joinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator;

  private final WorkEmployeeMapper workAttachmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkEmployeeRepository workAttachmentRepository;

  private final WorkService workService;

  @Override
  public List<WorkEmployeeResponseDto> getAllWorkEmployeesByWorkId(Long workFk) {
    return workAttachmentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(toWorkEmployeeResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkEmployeeResponseDto getWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeFk) {
    return toFullWorkEmployeeResponseDtoConverter.convert(getByWorkIdAndEmployeeId(workFk, employeeFk));
  }

  @Override
  public FullWorkEmployeeResponseDto createWorkEmployeeForWorkId(Long workFk, CreateWorkEmployeeRequestDto requestDto) {
    val work = workService.getById(workFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateWorkEmployeeRequestDtoConverter.convert(requestDto));

    val requestDtoWithWork = new JoinedCreateWorkEmployeeRequestDtoWithWorkAdapter(joinedRequestDto, work);
    joinedCreateWorkEmployeeRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workAttachment = workAttachmentRepository.save(Objects.requireNonNull(fromJoinedCreateWorkEmployeeRequestDtoWithWorkAdapterConverter.convert(requestDtoWithWork)));
    return toFullWorkEmployeeResponseDtoConverter.convert(workAttachment);
  }

  @Override
  public FullWorkEmployeeResponseDto updateWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeFk, UpdateWorkEmployeeRequestDto requestDto) {
    val workAttachment = getByWorkIdAndEmployeeId(workFk, employeeFk);

    return toFullWorkEmployeeResponseDtoConverter.convert(
      workAttachmentRepository.save(
        workAttachmentMapper.partialUpdate(workAttachment, requestDto)
      )
    );
  }

  @Override
  public void deleteWorkEmployeeByWorkIdAndEmployeeId(Long workFk, Long employeeFk) {
    workAttachmentRepository.delete(getByWorkIdAndEmployeeId(workFk, employeeFk));
  }

  @Override
  public WorkEmployee getByWorkIdAndEmployeeId(Long workFk, Long employeeFk) {
    return workAttachmentRepository.findByWorkIdAndEmployeeIdAndWorkUserId(workFk, employeeFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Zaposlenik koji je sudjelovao u radu"));
  }

}
