package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateRealisationRowRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationRowRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRealisationRowResponseDto;
import hr.algebra.fruity.dto.response.RealisationRowResponseDto;
import hr.algebra.fruity.service.RealisationRowService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping(RealisationRowController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RealisationRowController {

  private final RealisationRowService realisationRowService;

  @GetMapping(Mappings.getAllRealisationRowsGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationRowResponseDto>>> getAllRealisationRowsByRealisationId(@PathVariable Long realisationFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationRowService.getAllRealisationRowsByRealisationId(realisationFk),
        "Redovi uključeni u realizaciji uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getRealisationRowByIdGetMapping)
  public ResponseEntity<ApiResponse<FullRealisationRowResponseDto>> getRealisationRowByRealisationIdAndRowId(@PathVariable Long realisationFk, @PathVariable Long rowFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationRowService.getRealisationRowByRealisationIdAndRowId(realisationFk, rowFk),
        "Red uključen u realizaciji uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createRealisationRowPostMapping)
  public ResponseEntity<ApiResponse<?>> createRealisationRowForRealisationId(@PathVariable Long realisationFk, @Valid @RequestBody CreateRealisationRowRequestDto requestDto) {
    realisationRowService.createRealisationRowForRealisationId(realisationFk, requestDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        ApiResponse.created(
          "Red/ovi uspješno dodan/i u realizaciju."
        )
      );
  }

  @PutMapping(Mappings.updateRealisationRowByIdPutMapping)
  public ResponseEntity<ApiResponse<FullRealisationRowResponseDto>> updateRealisationRowByRealisationIdAndRowId(@PathVariable Long realisationFk, @PathVariable Long rowFk, @Valid @RequestBody UpdateRealisationRowRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationRowService.updateRealisationRowByRealisationIdAndRowId(realisationFk, rowFk, requestDto),
        "Red uključen u realizaciji uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRealisationRowByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRealisationRowByRealisationIdAndRowId(@PathVariable Long realisationFk, @PathVariable Long rowFk) {
    realisationRowService.deleteRealisationRowByRealisationIdAndRowId(realisationFk, rowFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Red uključen u realizaciji uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = RealisationController.Mappings.requestMapping + "/{realisationFk}/rows";

    public static final String getAllRealisationRowsGetMapping = "";

    public static final String getRealisationRowByIdGetMapping = "/{rowFk}";

    public static final String createRealisationRowPostMapping = "";

    public static final String updateRealisationRowByIdPutMapping = "/{rowFk}";

    public static final String deleteRealisationRowByIdDeleteMapping = "/{rowFk}";

  }

}