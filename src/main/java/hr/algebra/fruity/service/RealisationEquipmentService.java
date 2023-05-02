package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationEquipmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationEquipmentResponseDto;
import hr.algebra.fruity.model.RealisationEquipment;
import java.util.List;

public interface RealisationEquipmentService {

  List<RealisationEquipmentResponseDto> getAllRealisationEquipmentByRealisationId(Long realisationFk);

  FullRealisationEquipmentResponseDto getRealisationEquipmentByRealisationIdAndEquipmentId(Long realisationFk, Long equipmentFk);

  FullRealisationEquipmentResponseDto createRealisationEquipmentForRealisationId(Long realisationFk, CreateRealisationEquipmentRequestDto requestDto);

  FullRealisationEquipmentResponseDto updateRealisationEquipmentByRealisationIdAndEquipmentId(Long realisationFk, Long equipmentFk, UpdateRealisationEquipmentRequestDto requestDto);

  void deleteRealisationEquipmentByRealisationIdAndEquipmentId(Long realisationFk, Long equipmentFk);

  RealisationEquipment getByRealisationIdAndEquipmentId(Long realisationFk, Long equipmentFk);

}
