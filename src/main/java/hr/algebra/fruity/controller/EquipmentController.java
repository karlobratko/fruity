package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.EquipmentResponseDto;
import hr.algebra.fruity.service.EquipmentService;
import jakarta.validation.Valid;
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
@RequestMapping(EquipmentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class EquipmentController {

  private final EquipmentService equipmentService;

  @GetMapping(Mappings.getEquipmentByIdGetMapping)
  public ResponseEntity<ApiResponse<EquipmentResponseDto>> getEquipmentById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        equipmentService.getEquipmentById(id),
        "Oprema uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createEquipmentPostMapping)
  public ResponseEntity<ApiResponse<EquipmentResponseDto>> createEquipment(@Valid @RequestBody CreateEquipmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        equipmentService.createEquipment(requestDto),
        "Oprema uspješno dodana."
      )
    );
  }

  @PutMapping(Mappings.updateEquipmentByIdPutMapping)
  public ResponseEntity<ApiResponse<EquipmentResponseDto>> updateEquipmentById(@PathVariable Long id, @Valid @RequestBody UpdateEquipmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        equipmentService.updateEquipmentById(id, requestDto),
        "Oprema uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteEquipmentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteEquipmentById(@PathVariable Long id) {
    equipmentService.deleteEquipmentById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Oprema uspješno obrisana."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiResourceManagementRequestMapping + "/equipment";

    public static final String getEquipmentByIdGetMapping = "/{id}";

    public static final String createEquipmentPostMapping = "";

    public static final String updateEquipmentByIdPutMapping = "/{id}";

    public static final String deleteEquipmentByIdDeleteMapping = "/{id}";

  }

}
