package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.EquipmentMapper;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.EquipmentService;
import hr.algebra.fruity.validator.EquipmentWithUpdateEquipmentRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserEquipmentService implements EquipmentService {

  private final ConversionService conversionService;

  private final EquipmentWithUpdateEquipmentRequestDtoValidator equipmentWithUpdateEquipmentRequestDtoValidator;

  private final EquipmentMapper equipmentMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final EquipmentRepository equipmentRepository;

  @Override
  public List<EquipmentResponseDto> getAllEquipment() {
    return equipmentRepository.findAllByUser(currentRequestUserService.getUser()).stream()
      .map(equipment -> conversionService.convert(equipment, EquipmentResponseDto.class))
      .toList();
  }

  @Override
  public EquipmentResponseDto getEquipmentById(Long id) {
    return conversionService.convert(getEquipment(id), EquipmentResponseDto.class);
  }

  @Override
  public EquipmentResponseDto createEquipment(CreateEquipmentRequestDto requestDto) {
    val equipment = equipmentRepository.save(Objects.requireNonNull(conversionService.convert(requestDto, Equipment.class)));

    return conversionService.convert(equipment, EquipmentResponseDto.class);
  }

  @Override
  public EquipmentResponseDto updateEquipmentById(Long id, UpdateEquipmentRequestDto requestDto) {
    val equipment = getEquipment(id);

    equipmentWithUpdateEquipmentRequestDtoValidator.validate(equipment, requestDto);

    return conversionService.convert(
      equipmentRepository.save(
        equipmentMapper.partialUpdate(equipment, requestDto)
      ),
      EquipmentResponseDto.class
    );
  }

  @Override
  public void deleteEquipmentById(Long id) {
    equipmentRepository.delete(getEquipment(id));
  }

  private Equipment getEquipment(Long id) {
    val equipment = equipmentRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(equipment.getUser(), currentRequestUserService.getUser()))
      throw new ForeignUserDataAccessException();

    return equipment;
  }

}
