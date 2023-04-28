package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.service.ArcodeParcelService;
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
@RequestMapping(ArcodeParcelController.Mappings.requestMapping)
@RequiredArgsConstructor
public class ArcodeParcelController {

  private final ArcodeParcelService arcodeParcelService;

  @GetMapping(Mappings.getAllArcodeParcelsGetMapping)
  public ResponseEntity<ApiResponse<List<ArcodeParcelResponseDto>>> getAllArcodeParcels() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        arcodeParcelService.getAllArcodeParcels(),
        "ARKOD parcele uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getArcodeParcelByIdGetMapping)
  public ResponseEntity<ApiResponse<FullArcodeParcelResponseDto>> getArcodeParcelById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        arcodeParcelService.getArcodeParcelById(id),
        "ARKOD parcela uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createArcodeParcelPostMapping)
  public ResponseEntity<ApiResponse<FullArcodeParcelResponseDto>> createArcodeParcel(@Valid @RequestBody CreateArcodeParcelRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        arcodeParcelService.createArcodeParcel(requestDto),
        "ARKOD parcela uspješno dodana."
      )
    );
  }

  @PutMapping(Mappings.updateArcodeParcelByIdPutMapping)
  public ResponseEntity<ApiResponse<FullArcodeParcelResponseDto>> updateArcodeParcelById(@PathVariable Long id, @Valid @RequestBody UpdateArcodeParcelRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        arcodeParcelService.updateArcodeParcelById(id, requestDto),
        "ARKOD parcela uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteArcodeParcelByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteArcodeParcelById(@PathVariable Long id) {
    arcodeParcelService.deleteArcodeParcelById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "ARKOD parcela uspješno obrisana."
        )
      );
  }

  @GetMapping(Mappings.getAllRowClustersByArcodeParcelIdGetMapping)
  public ResponseEntity<ApiResponse<List<RowClusterResponseDto>>> getAllRowClustersByArcodeParcelId(@PathVariable Long id) {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          arcodeParcelService.getAllRowClustersByArcodeParcelId(id),
          "Table ARKOD parcele uspješno dohvaćene."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/arcode-parcels";

    public static final String getAllArcodeParcelsGetMapping = "";

    public static final String getArcodeParcelByIdGetMapping = "/{id}";

    public static final String createArcodeParcelPostMapping = "";

    public static final String updateArcodeParcelByIdPutMapping = "/{id}";

    public static final String deleteArcodeParcelByIdDeleteMapping = "/{id}";

    public static final String getAllRowClustersByArcodeParcelIdGetMapping = "/{id}/row-clusters";

  }

}