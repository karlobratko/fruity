package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRowResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.service.RowService;
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
@RequestMapping(RowController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RowController {

  private final RowService rowClusterService;

  @GetMapping(Mappings.getAllRowsGetMapping)
  public ResponseEntity<ApiResponse<List<RowResponseDto>>> getAllRow() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.getAllRows(),
        "Redovi uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getRowByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRowResponseDto>> getRowById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.getRowById(id),
        "Red uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createRowPostMapping)
  public ResponseEntity<ApiResponse<FullRowResponseDto>> createRow(@Valid @RequestBody CreateRowRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.createRow(requestDto),
        "Red uspješno dodan."
      )
    );
  }

  @PutMapping(Mappings.updateRowByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRowResponseDto>> updateRowById(@PathVariable Long id, @Valid @RequestBody UpdateRowRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.updateRowById(id, requestDto),
        "Red uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRowByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRowById(@PathVariable Long id) {
    rowClusterService.deleteRowById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Red uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/rows";

    public static final String getAllRowsGetMapping = "";

    public static final String getRowByIdGetMapping = "/{id}";

    public static final String createRowPostMapping = "";

    public static final String updateRowByIdPutMapping = "/{id}";

    public static final String deleteRowByIdDeleteMapping = "/{id}";

  }

}