package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationEquipmentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRealisationEquipmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationEquipmentResponseDto;
import hr.algebra.fruity.service.RealisationEquipmentService;
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
@RequestMapping(RealisationEquipmentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RealisationEquipmentController {

  private final RealisationEquipmentService realisationEquipmentService;

  @GetMapping(Mappings.getAllRealisationEquipmentGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationEquipmentResponseDto>>> getAllRealisationEquipmentByRealisationId(@PathVariable Long realisationFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationEquipmentService.getAllRealisationEquipmentByRealisationId(realisationFk),
        "Oprema korištena u realizaciji uspješno dohvaćena."
      )
    );
  }

  @GetMapping(Mappings.getRealisationEquipmentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRealisationEquipmentResponseDto>> getRealisationEquipmentByRealisationIdAndEquipmentId(@PathVariable Long realisationFk, @PathVariable Long equipmentFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationEquipmentService.getRealisationEquipmentByRealisationIdAndEquipmentId(realisationFk, equipmentFk),
        "Oprema korištena u realizaciji uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createRealisationEquipmentPostMapping)
  public ResponseEntity<ApiResponse<FullRealisationEquipmentResponseDto>> createRealisationEquipmentForRealisationId(@PathVariable Long realisationFk, @Valid @RequestBody CreateRealisationEquipmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationEquipmentService.createRealisationEquipmentForRealisationId(realisationFk, requestDto),
        "Oprema uspješno dodana u realizaciju."
      )
    );
  }

  @PutMapping(Mappings.updateRealisationEquipmentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRealisationEquipmentResponseDto>> updateRealisationEquipmentByRealisationIdAndEquipmentId(@PathVariable Long realisationFk, @PathVariable Long equipmentFk, @Valid @RequestBody UpdateRealisationEquipmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationEquipmentService.updateRealisationEquipmentByRealisationIdAndEquipmentId(realisationFk, equipmentFk, requestDto),
        "Oprema korištena u realizaciji uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRealisationEquipmentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRealisationEquipmentByRealisationIdAndEquipmentId(@PathVariable Long realisationFk, @PathVariable Long equipmentFk) {
    realisationEquipmentService.deleteRealisationEquipmentByRealisationIdAndEquipmentId(realisationFk, equipmentFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Oprema korištena u realizaciji uspješno obrisana."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = RealisationController.Mappings.requestMapping + "/{realisationFk}/equipment";

    public static final String getAllRealisationEquipmentGetMapping = "";

    public static final String getRealisationEquipmentByIdGetMapping = "/{equipmentFk}";

    public static final String createRealisationEquipmentPostMapping = "";

    public static final String updateRealisationEquipmentByIdPutMapping = "/{equipmentFk}";

    public static final String deleteRealisationEquipmentByIdDeleteMapping = "/{equipmentFk}";

  }

}