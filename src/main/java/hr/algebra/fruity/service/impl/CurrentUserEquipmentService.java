package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.AttachmentToAttachmentResponseDtoConverter;
import hr.algebra.fruity.converter.CreateEquipmentRequestDtoToJoinedCreateEquipmentRequestDtoConverter;
import hr.algebra.fruity.converter.EquipmentToEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.EquipmentToFullEquipmentResponseDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateEquipmentRequestDtoToEquipmentConverter;
import hr.algebra.fruity.converter.UpdateEquipmentRequestDtoToJoinedUpdateEquipmentRequestDtoConverter;
import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.dto.response.FullEquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.EquipmentMapper;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.AttachmentRepository;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.EquipmentService;
import hr.algebra.fruity.validator.EquipmentWithJoinedUpdateEquipmentRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateEquipmentRequestDtoValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserEquipmentService implements EquipmentService {

  private final EquipmentToEquipmentResponseDtoConverter toEquipmentResponseDtoConverter;

  private final EquipmentToFullEquipmentResponseDtoConverter toFullEquipmentResponseDtoConverter;

  private final CreateEquipmentRequestDtoToJoinedCreateEquipmentRequestDtoConverter toJoinedCreateEquipmentRequestDtoConverter;

  private final JoinedCreateEquipmentRequestDtoToEquipmentConverter fromJoinedCreateEquipmentRequestDtoConverter;

  private final UpdateEquipmentRequestDtoToJoinedUpdateEquipmentRequestDtoConverter toJoinedUpdateEquipmentRequestDtoConverter;

  private final AttachmentToAttachmentResponseDtoConverter toAttachmentResponseDtoConverter;

  private final JoinedCreateEquipmentRequestDtoValidator joinedCreateEquipmentRequestDtoValidator;

  private final EquipmentWithJoinedUpdateEquipmentRequestDtoValidator equipmentWithJoinedUpdateEquipmentRequestDtoValidator;

  private final EquipmentMapper equipmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final EquipmentRepository equipmentRepository;

  private final AttachmentRepository attachmentRepository;

  @Override
  public List<EquipmentResponseDto> getAllEquipment() {
    return equipmentRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toEquipmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public List<AttachmentResponseDto> getAllAttachmentsByEquipmentId(Long equipmentFk) {
    return attachmentRepository.findAllByEquipment(getById(equipmentFk)).stream()
      .map(toAttachmentResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullEquipmentResponseDto getEquipmentById(Long id) {
    return toFullEquipmentResponseDtoConverter.convert(getById(id));
  }

  @Override
  @Transactional
  public FullEquipmentResponseDto createEquipment(CreateEquipmentRequestDto requestDto) {
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateEquipmentRequestDtoConverter.convert(requestDto));

    joinedCreateEquipmentRequestDtoValidator.validate(joinedRequestDto);

    val equipment = equipmentRepository.save(Objects.requireNonNull(fromJoinedCreateEquipmentRequestDtoConverter.convert(joinedRequestDto)));

    return toFullEquipmentResponseDtoConverter.convert(equipment);
  }

  @Override
  @Transactional
  public FullEquipmentResponseDto updateEquipmentById(Long id, UpdateEquipmentRequestDto requestDto) {
    val equipment = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateEquipmentRequestDtoConverter.convert(requestDto));

    equipmentWithJoinedUpdateEquipmentRequestDtoValidator.validate(equipment, joinedRequestDto);

    return toFullEquipmentResponseDtoConverter.convert(
      equipmentRepository.save(
        equipmentMapper.partialUpdate(equipment, joinedRequestDto)
      )
    );
  }

  @Override
  @Transactional
  public void deleteEquipmentById(Long id) {
    equipmentRepository.delete(getById(id));
  }

  @Override
  public Equipment getById(Long id) {
    return equipmentRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Oprema"));
  }

}
