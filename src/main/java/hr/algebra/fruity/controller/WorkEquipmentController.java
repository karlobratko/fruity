package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkEquipmentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullWorkEquipmentResponseDto;
import hr.algebra.fruity.dto.response.WorkEquipmentResponseDto;
import hr.algebra.fruity.service.WorkEquipmentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WorkEquipmentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkEquipmentController {

  private final WorkEquipmentService workEquipment;

  @GetMapping(Mappings.getAllWorkEquipmentGetMapping)
  public ResponseEntity<ApiResponse<List<WorkEquipmentResponseDto>>> getAllWorkEquipmentByWorkId(@PathVariable Long workFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEquipment.getAllWorkEquipmentByWorkId(workFk),
        "Oprema korištena u radu uspješno dohvaćena."
      )
    );
  }

  @GetMapping(Mappings.getWorkEquipmentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkEquipmentResponseDto>> getWorkEquipmentByWorkIdAndEquipmentId(@PathVariable Long workFk, @PathVariable Long equipmentFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEquipment.getWorkEquipmentByWorkIdAndEquipmentId(workFk, equipmentFk),
        "Oprema korištena u radu uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createWorkEquipmentPostMapping)
  public ResponseEntity<ApiResponse<FullWorkEquipmentResponseDto>> createWorkEquipmentForWorkId(@PathVariable Long workFk, @Valid @RequestBody CreateWorkEquipmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEquipment.createWorkEquipmentForWorkId(workFk, requestDto),
        "Oprema uspješno dodana u rad."
      )
    );
  }

  @PutMapping(Mappings.updateWorkEquipmentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkEquipmentResponseDto>> updateWorkEquipmentByWorkIdAndEquipmentId(@PathVariable Long workFk, @PathVariable Long equipmentFk, @Valid @RequestBody UpdateWorkEquipmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEquipment.updateWorkEquipmentByWorkIdAndEquipmentId(workFk, equipmentFk, requestDto),
        "Oprema korištena u radu uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkEquipmentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkEquipmentByWorkIdAndEquipmentId(@PathVariable Long workFk, @PathVariable Long equipmentFk) {
    workEquipment.deleteWorkEquipmentByWorkIdAndEquipmentId(workFk, equipmentFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Oprema korištena u radu uspješno obrisana."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = WorkController.Mappings.requestMapping + "/{workFk}/equipment";

    public static final String getAllWorkEquipmentGetMapping = "";

    public static final String getWorkEquipmentByIdGetMapping = "/{equipmentFk}";

    public static final String createWorkEquipmentPostMapping = "";

    public static final String updateWorkEquipmentByIdPutMapping = "/{equipmentFk}";

    public static final String deleteWorkEquipmentByIdDeleteMapping = "/{equipmentFk}";

  }

}