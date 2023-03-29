package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import java.util.List;

public interface EquipmentService {

  List<EquipmentResponseDto> getAllEquipment();

  EquipmentResponseDto getEquipmentById(Long id);

  EquipmentResponseDto createEquipment(CreateEquipmentRequestDto requestDto);

  EquipmentResponseDto updateEquipmentById(Long id, UpdateEquipmentRequestDto requestDto);

  void deleteEquipmentById(Long id);

}
