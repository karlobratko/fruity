package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateRealisationRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRealisationResponseDto;
import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.service.RealisationService;
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
@RequestMapping(RealisationController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RealisationController {

  private final RealisationService realisationService;

  @GetMapping(Mappings.getAllRealisationsGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationResponseDto>>> getAllRealisations() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationService.getAllRealisations(),
        "Realizacije uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getRealisationByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRealisationResponseDto>> getRealisationById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationService.getRealisationById(id),
        "Realizacija uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createRealisationPostMapping)
  public ResponseEntity<ApiResponse<FullRealisationResponseDto>> createRealisation(@Valid @RequestBody CreateRealisationRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationService.createRealisation(requestDto),
        "Realizacija uspješno dodana."
      )
    );
  }

  @PutMapping(Mappings.updateRealisationByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRealisationResponseDto>> updateRealisationById(@PathVariable Long id, @Valid @RequestBody UpdateRealisationRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationService.updateRealisationById(id, requestDto),
        "Realizacija uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRealisationByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRealisationById(@PathVariable Long id) {
    realisationService.deleteRealisationById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Realizacija uspješno obrisana."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiWorkManagementRequestMapping + "/realisations";

    public static final String getAllRealisationsGetMapping = "";

    public static final String getRealisationByIdGetMapping = "/{id}";

    public static final String createRealisationPostMapping = "";

    public static final String updateRealisationByIdPutMapping = "/{id}";

    public static final String deleteRealisationByIdDeleteMapping = "/{id}";

  }

}