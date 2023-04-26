package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateWorkRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullWorkRowResponseDto;
import hr.algebra.fruity.dto.response.WorkRowResponseDto;
import hr.algebra.fruity.service.WorkRowService;
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
@RequestMapping(WorkRowController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkRowController {

  private final WorkRowService workRowService;

  @GetMapping(Mappings.getAllWorkRowsGetMapping)
  public ResponseEntity<ApiResponse<List<WorkRowResponseDto>>> getAllWorkRowsByWorkId(@PathVariable Long workFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workRowService.getAllWorkRowsByWorkId(workFk),
        "Redovi uključeni u radu uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getWorkRowByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkRowResponseDto>> getWorkRowByWorkIdAndRowId(@PathVariable Long workFk, @PathVariable Long rowFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workRowService.getWorkRowByWorkIdAndRowId(workFk, rowFk),
        "Red uključen u radu uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createWorkRowPostMapping)
  public ResponseEntity<ApiResponse<FullWorkRowResponseDto>> createWorkRowForWorkId(@PathVariable Long workFk, @Valid @RequestBody CreateWorkRowRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workRowService.createWorkRowForWorkId(workFk, requestDto),
        "Red uspješno dodan u rad."
      )
    );
  }

  @PutMapping(Mappings.updateWorkRowByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkRowResponseDto>> updateWorkRowByWorkIdAndRowId(@PathVariable Long workFk, @PathVariable Long rowFk, @Valid @RequestBody UpdateWorkRowRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workRowService.updateWorkRowByWorkIdAndRowId(workFk, rowFk, requestDto),
        "Red uključen u radu uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkRowByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkRowByWorkIdAndRowId(@PathVariable Long workFk, @PathVariable Long rowFk) {
    workRowService.deleteWorkRowByWorkIdAndRowId(workFk, rowFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Red uključen u radu uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = WorkController.Mappings.requestMapping + "/{workFk}/rows";

    public static final String getAllWorkRowsGetMapping = "";

    public static final String getWorkRowByIdGetMapping = "/{rowFk}";

    public static final String createWorkRowPostMapping = "";

    public static final String updateWorkRowByIdPutMapping = "/{rowFk}";

    public static final String deleteWorkRowByIdDeleteMapping = "/{rowFk}";

  }

}