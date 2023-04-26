package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.dto.request.UpdateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.dto.response.WorkEquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.WorkEquipmentMapper;
import hr.algebra.fruity.model.WorkEquipment;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.WorkEquipmentService;
import hr.algebra.fruity.service.WorkService;
import hr.algebra.fruity.validator.CreateWorkEquipmentRequestDtoWithWorkAdapterValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserWorkEquipmentService implements WorkEquipmentService {

  private final ConversionService conversionService;

  private final CreateWorkEquipmentRequestDtoWithWorkAdapterValidator createWorkEquipmentRequestDtoWithWorkAdapterValidator;

  private final WorkEquipmentMapper workEquipmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final WorkEquipmentRepository workEquipmentRepository;

  private final WorkService workService;

  @Override
  public List<WorkEquipmentResponseDto> getAllWorkEquipmentByWorkId(Long workFk) {
    return workEquipmentRepository.findAllByWork(workService.getById(workFk)).stream()
      .map(workEquipment -> conversionService.convert(workEquipment, WorkEquipmentResponseDto.class))
      .toList();
  }

  @Override
  public FullWorkEquipmentResponseDto getWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk) {
    return conversionService.convert(getByWorkIdAndEquipmentId(workFk, equipmentFk), FullWorkEquipmentResponseDto.class);
  }

  @Override
  public FullWorkEquipmentResponseDto createWorkEquipmentForWorkId(Long workFk, CreateWorkEquipmentRequestDto requestDto) {
    val work = workService.getById(workFk);

    val requestDtoWithWork = new CreateWorkEquipmentRequestDtoWithWorkAdapter(requestDto, work);
    createWorkEquipmentRequestDtoWithWorkAdapterValidator.validate(requestDtoWithWork);

    val workEquipment = workEquipmentRepository.save(Objects.requireNonNull(conversionService.convert(requestDtoWithWork, WorkEquipment.class)));
    return conversionService.convert(workEquipment, FullWorkEquipmentResponseDto.class);
  }

  @Override
  public FullWorkEquipmentResponseDto updateWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk, UpdateWorkEquipmentRequestDto requestDto) {
    val workEquipment = getByWorkIdAndEquipmentId(workFk, equipmentFk);

    return conversionService.convert(
      workEquipmentRepository.save(
        workEquipmentMapper.partialUpdate(workEquipment, requestDto)
      ),
      FullWorkEquipmentResponseDto.class
    );
  }

  @Override
  public void deleteWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk) {
    workEquipmentRepository.delete(getByWorkIdAndEquipmentId(workFk, equipmentFk));
  }

  @Override
  public WorkEquipment getByWorkIdAndEquipmentId(Long workFk, Long equipmentFk) {
    val workEquipment = workEquipmentRepository.findByWorkIdAndEquipmentId(workFk, equipmentFk)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(workEquipment.getWork().getUser().getId(), currentRequestUserService.getUserId()))
      throw new ForeignUserDataAccessException();

    return workEquipment;
  }

}
