package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationAttachmentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRealisationAttachmentResponseDto;
import hr.algebra.fruity.dto.response.RealisationAttachmentResponseDto;
import hr.algebra.fruity.service.RealisationAttachmentService;
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
@RequestMapping(RealisationAttachmentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RealisationAttachmentController {

  private final RealisationAttachmentService realisationAttachmentService;

  @GetMapping(Mappings.getAllRealisationAttachmentsGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationAttachmentResponseDto>>> getAllRealisationAttachmentsByRealisationId(@PathVariable Long realisationFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAttachmentService.getAllRealisationAttachmentsByRealisationId(realisationFk),
        "Priključci korišteni u realizaciji uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getRealisationAttachmentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRealisationAttachmentResponseDto>> getRealisationAttachmentByRealisationIdAndAttachmentId(@PathVariable Long realisationFk, @PathVariable Long attachmentFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAttachmentService.getRealisationAttachmentByRealisationIdAndAttachmentId(realisationFk, attachmentFk),
        "Priključak korišten u realizaciji uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createRealisationAttachmentPostMapping)
  public ResponseEntity<ApiResponse<FullRealisationAttachmentResponseDto>> createRealisationAttachmentForRealisationId(@PathVariable Long realisationFk, @Valid @RequestBody CreateRealisationAttachmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAttachmentService.createRealisationAttachmentForRealisationId(realisationFk, requestDto),
        "Priključak uspješno dodan u realizaciju."
      )
    );
  }

  @PutMapping(Mappings.updateRealisationAttachmentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRealisationAttachmentResponseDto>> updateRealisationAttachmentByRealisationIdAndAttachmentId(@PathVariable Long realisationFk, @PathVariable Long attachmentFk, @Valid @RequestBody UpdateRealisationAttachmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationAttachmentService.updateRealisationAttachmentByRealisationIdAndAttachmentId(realisationFk, attachmentFk, requestDto),
        "Priključak korišten u realizaciji uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRealisationAttachmentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRealisationAttachmentByRealisationIdAndAttachmentId(@PathVariable Long realisationFk, @PathVariable Long attachmentFk) {
    realisationAttachmentService.deleteRealisationAttachmentByRealisationIdAndAttachmentId(realisationFk, attachmentFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Priključak korišten u realizaciji uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = RealisationController.Mappings.requestMapping + "/{realisationFk}/attachments";

    public static final String getAllRealisationAttachmentsGetMapping = "";

    public static final String getRealisationAttachmentByIdGetMapping = "/{attachmentFk}";

    public static final String createRealisationAttachmentPostMapping = "";

    public static final String updateRealisationAttachmentByIdPutMapping = "/{attachmentFk}";

    public static final String deleteRealisationAttachmentByIdDeleteMapping = "/{attachmentFk}";

  }

}