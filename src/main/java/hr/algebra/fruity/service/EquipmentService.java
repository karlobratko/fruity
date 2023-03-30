package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.dto.response.FullEquipmentResponseDto;
import java.util.List;

public interface EquipmentService {

  List<EquipmentResponseDto> getAllEquipment();

  FullEquipmentResponseDto getEquipmentById(Long id);

  FullEquipmentResponseDto createEquipment(CreateEquipmentRequestDto requestDto);

  FullEquipmentResponseDto updateEquipmentById(Long id, UpdateEquipmentRequestDto requestDto);

  void deleteEquipmentById(Long id);

}
