package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateAttachmentRequestDto;
import hr.algebra.fruity.dto.request.UpdateAttachmentRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.AttachmentResponseDto;
import hr.algebra.fruity.service.AttachmentService;
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
@RequestMapping(AttachmentController.Mappings.requestMapping)
@RequiredArgsConstructor
public class AttachmentController {

  private final AttachmentService attachmentService;

  @GetMapping(Mappings.getAllAttachmentGetMapping)
  public ResponseEntity<ApiResponse<List<AttachmentResponseDto>>> getAllAttachment() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        attachmentService.getAllAttachments(),
        "Priključci uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getAttachmentByIdGetMapping)
  public ResponseEntity<ApiResponse<AttachmentResponseDto>> getAttachmentById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        attachmentService.getAttachmentById(id),
        "Priključak uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createAttachmentPostMapping)
  public ResponseEntity<ApiResponse<AttachmentResponseDto>> createAttachment(@Valid @RequestBody CreateAttachmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        attachmentService.createAttachment(requestDto),
        "Priključak uspješno dodan."
      )
    );
  }

  @PutMapping(Mappings.updateAttachmentByIdPutMapping)
  public ResponseEntity<ApiResponse<AttachmentResponseDto>> updateAttachmentById(@PathVariable Long id, @Valid @RequestBody UpdateAttachmentRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        attachmentService.updateAttachmentById(id, requestDto),
        "Priključak uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteAttachmentByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteAttachmentById(@PathVariable Long id) {
    attachmentService.deleteAttachmentById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Priključak uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiResourceManagementRequestMapping + "/attachments";

    public static final String getAllAttachmentGetMapping = "";

    public static final String getAttachmentByIdGetMapping = "/{id}";

    public static final String createAttachmentPostMapping = "";

    public static final String updateAttachmentByIdPutMapping = "/{id}";

    public static final String deleteAttachmentByIdDeleteMapping = "/{id}";

  }

}