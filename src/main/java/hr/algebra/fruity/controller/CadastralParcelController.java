package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import hr.algebra.fruity.service.CadastralParcelService;
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
@RequestMapping(CadastralParcelController.Mappings.requestMapping)
@RequiredArgsConstructor
public class CadastralParcelController {

  private final CadastralParcelService cadastralParcelService;

  @GetMapping(Mappings.getAllCadastralParcelsGetMapping)
  public ResponseEntity<ApiResponse<List<CadastralParcelResponseDto>>> getAllCadastralParcels() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralParcelService.getAllCadastralParcels(),
        "Katastarske čestice uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getCadastralParcelByIdGetMapping)
  public ResponseEntity<ApiResponse<FullCadastralParcelResponseDto>> getCadastralParcelById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralParcelService.getCadastralParcelById(id),
        "Katastarska čestica uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createCadastralParcelPostMapping)
  public ResponseEntity<ApiResponse<FullCadastralParcelResponseDto>> createCadastralParcel(@Valid @RequestBody CreateCadastralParcelRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralParcelService.createCadastralParcel(requestDto),
        "Katastarska čestica uspješno dodana."
      )
    );
  }

  @PutMapping(Mappings.updateCadastralParcelByIdPutMapping)
  public ResponseEntity<ApiResponse<FullCadastralParcelResponseDto>> updateCadastralParcelById(@PathVariable Long id, @Valid @RequestBody UpdateCadastralParcelRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralParcelService.updateCadastralParcelById(id, requestDto),
        "Katastarska čestica uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteCadastralParcelByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteCadastralParcelById(@PathVariable Long id) {
    cadastralParcelService.deleteCadastralParcelById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Katastarska čestica uspješno obrisana."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/cadastral-parcels";

    public static final String getAllCadastralParcelsGetMapping = "";

    public static final String getCadastralParcelByIdGetMapping = "/{id}";

    public static final String createCadastralParcelPostMapping = "";

    public static final String updateCadastralParcelByIdPutMapping = "/{id}";

    public static final String deleteCadastralParcelByIdDeleteMapping = "/{id}";

  }

}
