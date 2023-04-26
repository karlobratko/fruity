package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkAttachmentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullWorkAttachmentResponseDto;
import hr.algebra.fruity.dto.response.WorkAttachmentResponseDto;
import hr.algebra.fruity.service.WorkAttachmentService;
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
@RequestMapping(WorkAttachmentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkAttachmentController {

  private final WorkAttachmentService workAttachment;

  @GetMapping(Mappings.getAllWorkAttachmentsGetMapping)
  public ResponseEntity<ApiResponse<List<WorkAttachmentResponseDto>>> getAllWorkAttachmentsByWorkId(@PathVariable Long workFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workAttachment.getAllWorkAttachmentsByWorkId(workFk),
        "Priključci korišteni u radu uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getWorkAttachmentByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkAttachmentResponseDto>> getWorkAttachmentByWorkIdAndAttachmentId(@PathVariable Long workFk, @PathVariable Long attachmentFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workAttachment.getWorkAttachmentByWorkIdAndAttachmentId(workFk, attachmentFk),
        "Priključak korišten u radu uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createWorkAttachmentPostMapping)
  public ResponseEntity<ApiResponse<FullWorkAttachmentResponseDto>> createWorkAttachmentForWorkId(@PathVariable Long workFk, @Valid @RequestBody CreateWorkAttachmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workAttachment.createWorkAttachmentForWorkId(workFk, requestDto),
        "Priključak uspješno dodan u rad."
      )
    );
  }

  @PutMapping(Mappings.updateWorkAttachmentByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkAttachmentResponseDto>> updateWorkAttachmentByWorkIdAndAttachmentId(@PathVariable Long workFk, @PathVariable Long attachmentFk, @Valid @RequestBody UpdateWorkAttachmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workAttachment.updateWorkAttachmentByWorkIdAndAttachmentId(workFk, attachmentFk, requestDto),
        "Priključak korišten u radu uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkAttachmentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkAttachmentByWorkIdAndAttachmentId(@PathVariable Long workFk, @PathVariable Long attachmentFk) {
    workAttachment.deleteWorkAttachmentByWorkIdAndAttachmentId(workFk, attachmentFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Priključak korišten u radu uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = WorkController.Mappings.requestMapping + "/{workFk}/attachments";

    public static final String getAllWorkAttachmentsGetMapping = "";

    public static final String getWorkAttachmentByIdGetMapping = "/{attachmentFk}";

    public static final String createWorkAttachmentPostMapping = "";

    public static final String updateWorkAttachmentByIdPutMapping = "/{attachmentFk}";

    public static final String deleteWorkAttachmentByIdDeleteMapping = "/{attachmentFk}";

  }

}