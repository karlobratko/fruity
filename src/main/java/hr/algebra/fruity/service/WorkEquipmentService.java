package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.dto.response.WorkEquipmentResponseDto;
import hr.algebra.fruity.model.WorkEquipment;
import java.util.List;

public interface WorkEquipmentService {

  List<WorkEquipmentResponseDto> getAllWorkEquipmentByWorkId(Long workFk);

  FullWorkEquipmentResponseDto getWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk);

  FullWorkEquipmentResponseDto createWorkEquipmentForWorkId(Long workFk, CreateWorkEquipmentRequestDto requestDto);

  FullWorkEquipmentResponseDto updateWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk, UpdateWorkEquipmentRequestDto requestDto);

  void deleteWorkEquipmentByWorkIdAndEquipmentId(Long workFk, Long equipmentFk);

  WorkEquipment getByWorkIdAndEquipmentId(Long workFk, Long equipmentFk);

}
