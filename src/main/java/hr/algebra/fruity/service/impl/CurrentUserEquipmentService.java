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
import hr.algebra.fruity.repository.RealisationEquipmentRepository;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.EquipmentService;
import hr.algebra.fruity.validator.EquipmentWithJoinedUpdateEquipmentRequestDtoValidator;
import hr.algebra.fruity.validator.JoinedCreateEquipmentRequestDtoValidator;
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

  private final WorkEquipmentRepository workEquipmentRepository;

  private final RealisationEquipmentRepository realisationEquipmentRepository;

  private final EntityManager entityManager;

  @Override
  public List<EquipmentResponseDto> getAllEquipment() {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val equipment = equipmentRepository.findAllByUserId(currentRequestUserService.getUserId()).stream()
      .map(toEquipmentResponseDtoConverter::convert)
      .toList();

    session.disableFilter("isNotDeleted");

    return equipment;
  }

  @Override
  public List<AttachmentResponseDto> getAllAttachmentsByEquipmentId(Long equipmentFk) {
    val equipment = getById(equipmentFk);

    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val attachments = attachmentRepository.findAllByEquipment(equipment).stream()
      .map(toAttachmentResponseDtoConverter::convert)
      .toList();

    session.disableFilter("isNotDeleted");

    return attachments;
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

    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val equipment = equipmentRepository.save(Objects.requireNonNull(fromJoinedCreateEquipmentRequestDtoConverter.convert(joinedRequestDto)));

    session.disableFilter("isNotDeleted");

    return toFullEquipmentResponseDtoConverter.convert(equipment);
  }

  @Override
  @Transactional
  public FullEquipmentResponseDto updateEquipmentById(Long id, UpdateEquipmentRequestDto requestDto) {
    val equipment = getById(id);
    val joinedRequestDto = Objects.requireNonNull(toJoinedUpdateEquipmentRequestDtoConverter.convert(requestDto));

    equipmentWithJoinedUpdateEquipmentRequestDtoValidator.validate(equipment, joinedRequestDto);

    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val savedEquipment = equipmentRepository.save(equipmentMapper.partialUpdate(equipment, joinedRequestDto));

    session.disableFilter("isNotDeleted");

    return toFullEquipmentResponseDtoConverter.convert(savedEquipment);
  }

  @Override
  @Transactional
  public void deleteEquipmentById(Long id) {
    val equipment = getById(id);

    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    workEquipmentRepository.deleteByEquipmentAndWorkFinishedFalse(equipment);
    realisationEquipmentRepository.deleteByEquipmentAndRealisationWorkFinishedFalse(equipment);

    equipmentRepository.delete(equipment);

    session.disableFilter("isNotDeleted");
  }

  @Override
  public Equipment getById(Long id) {
    val session = entityManager.unwrap(Session.class);
    session.enableFilter("isNotDeleted");

    val equipment = equipmentRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Oprema"));

    session.disableFilter("isNotDeleted");

    return equipment;
  }

  @Override
  public Equipment getByIdIgnoreSoftDelete(Long id) {
    return equipmentRepository.findByIdAndUserId(id, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Oprema"));
  }

}
