package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.dto.response.RowResponseDto;
import hr.algebra.fruity.service.RowClusterService;
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
@RequestMapping(RowClusterController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RowClusterController {

  private final RowClusterService rowClusterService;

  private final RowService rowService;

  @GetMapping(Mappings.getAllRowClustersGetMapping)
  public ResponseEntity<ApiResponse<List<RowClusterResponseDto>>> getAllRowCluster() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.getAllRowClusters(),
        "Table uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getRowClusterByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRowClusterResponseDto>> getRowClusterById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.getRowClusterById(id),
        "Tabla uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createRowClusterPostMapping)
  public ResponseEntity<ApiResponse<FullRowClusterResponseDto>> createRowCluster(@Valid @RequestBody CreateRowClusterRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.createRowCluster(requestDto),
        "Tabla uspješno dodana."
      )
    );
  }

  @PutMapping(Mappings.updateRowClusterByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRowClusterResponseDto>> updateRowClusterById(@PathVariable Long id, @Valid @RequestBody UpdateRowClusterRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        rowClusterService.updateRowClusterById(id, requestDto),
        "Tabla uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRowClusterByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRowClusterById(@PathVariable Long id) {
    rowClusterService.deleteRowClusterById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Tabla uspješno obrisana."
        )
      );
  }

  @GetMapping(Mappings.getAllRowsByRowClusterIdGetMapping)
  public ResponseEntity<ApiResponse<List<RowResponseDto>>> getAllRowsByRowClusterId(@PathVariable Long id) {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          rowService.getAllRowsByRowClusterId(id),
          "Redovi table uspješno dohvaćeni."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/row-clusters";

    public static final String getAllRowClustersGetMapping = "";

    public static final String getRowClusterByIdGetMapping = "/{id}";

    public static final String createRowClusterPostMapping = "";

    public static final String updateRowClusterByIdPutMapping = "/{id}";

    public static final String deleteRowClusterByIdDeleteMapping = "/{id}";

    public static final String getAllRowsByRowClusterIdGetMapping = "/{id}/rows";

  }

}