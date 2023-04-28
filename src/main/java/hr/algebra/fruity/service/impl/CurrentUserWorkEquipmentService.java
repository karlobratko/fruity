package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter;
import hr.algebra.fruity.converter.WorkEquipmentToFullWorkEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.WorkEquipmentToWorkEquipmentResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.dto.response.WorkEquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.WorkEquipmentMapper;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkEquipmentService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkEquipmentService implements WorkEquipmentService {

  private final WorkEquipmentToWorkEquipmentResponseDtoConverter toWorkEquipmentResponseDtoConverter;

  private final WorkEquipmentToFullWorkEquipmentResponseDtoConverter toFullWorkEquipmentResponseDtoConverter;

  private final CreateWorkEquipmentRequestDtoToJoinedCreateWorkEquipmentRequestDtoConverter toJoinedCreateWorkEquipmentRequestDtoConverter;

  private final JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterToWorkEquipmentConverter fromJoinedCreateWorkEquipmentRequestDtoWithWorkAdapterConverter;

  private final JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator joinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator;

  private final WorkEquipmentMapper workEquipmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkEquipmentRepository workEquipmentRepository;

  private final WorkService workService;

  @Override
  public List<WorkEquipmentResponseDto> getAllWorkEquipmentByWorkId(Long workFk) {
    return workEquipmentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(toWorkEquipmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullWorkEquipmentResponseDto getWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk) {
    return toFullWorkEquipmentResponseDtoConverter.convert(getByWorkIdAndEquipmentId(workFk, equipmentFk));
  }

  @Override
  public FullWorkEquipmentResponseDto createWorkEquipmentForWorkId(Long workFk, CreateWorkEquipmentRequestDto requestDto) {
    val work = workService.getById(workFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateWorkEquipmentRequestDtoConverter.convert(requestDto));

    val requestDtoWithWork = new JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter(joinedRequestDto, work);
    joinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workEquipment = workEquipmentRepository.save(Objects.requireNonNull(fromJoinedCreateWorkEquipmentRequestDtoWithWorkAdapterConverter.convert(requestDtoWithWork)));
    return toFullWorkEquipmentResponseDtoConverter.convert(workEquipment);
  }

  @Override
  public FullWorkEquipmentResponseDto updateWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk, UpdateWorkEquipmentRequestDto requestDto) {
    val workEquipment = getByWorkIdAndEquipmentId(workFk, equipmentFk);

    return toFullWorkEquipmentResponseDtoConverter.convert(
      workEquipmentRepository.save(
        workEquipmentMapper.partialUpdate(workEquipment, requestDto)
      )
    );
  }

  @Override
  public void deleteWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk) {
    workEquipmentRepository.delete(getByWorkIdAndEquipmentId(workFk, equipmentFk));
  }

  @Override
  public WorkEquipment getByWorkIdAndEquipmentId(Long workFk, Long equipmentFk) {
    return workEquipmentRepository.findByWorkIdAndEquipmentIdAndWorkUserId(workFk, equipmentFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Oprema korištena u radu"));
  }

}
