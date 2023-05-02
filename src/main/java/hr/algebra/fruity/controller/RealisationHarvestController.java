package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullRealisationHarvestResponseDto;
import hr.algebra.fruity.dto.response.RealisationHarvestResponseDto;
import hr.algebra.fruity.service.RealisationHarvestService;
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
@RequestMapping(RealisationHarvestController.Mappings.requestMapping)
@RequiredArgsConstructor
public class RealisationHarvestController {

  private final RealisationHarvestService realisationHarvestService;

  @GetMapping(Mappings.getAllRealisationHarvestsGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationHarvestResponseDto>>> getAllRealisationHarvestsByRealisationId(@PathVariable Long realisationFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationHarvestService.getAllRealisationHarvestsByRealisationId(realisationFk),
        "Berbe odrađene u realizaciji uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getAllRealisationHarvestsByFruitCultivarIdGetMapping)
  public ResponseEntity<ApiResponse<List<RealisationHarvestResponseDto>>> getAllRealisationHarvestsByFruitCultivarId(@PathVariable Long realisationFk, @PathVariable Integer fruitCultivarFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationHarvestService.getAllRealisationHarvestsByRealisationIdAndFruitCultivarId(realisationFk, fruitCultivarFk),
        "Berbe odrađene u realizaciji uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getRealisationHarvestByFruitCultivarIdAndClassIdGetMapping)
  public ResponseEntity<ApiResponse<FullRealisationHarvestResponseDto>> getRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(@PathVariable Long realisationFk, @PathVariable Integer fruitCultivarFk, @PathVariable Integer classFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationHarvestService.getRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(realisationFk, fruitCultivarFk, classFk),
        "Berba odrađena u realizaciji uspješno dohvaćena."
      )
    );
  }

  @PostMapping(Mappings.createRealisationHarvestPostMapping)
  public ResponseEntity<ApiResponse<FullRealisationHarvestResponseDto>> createRealisationHarvestForRealisationId(@PathVariable Long realisationFk, @Valid @RequestBody CreateRealisationHarvestRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationHarvestService.createRealisationHarvestForRealisationId(realisationFk, requestDto),
        "Berba dodana u realizaciju."
      )
    );
  }

  @PutMapping(Mappings.updateRealisationHarvestByByFruitCultivarIdAndClassIdPutMapping)
  public ResponseEntity<ApiResponse<FullRealisationHarvestResponseDto>> updateRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(@PathVariable Long realisationFk, @PathVariable Integer fruitCultivarFk, @PathVariable Integer classFk, @Valid @RequestBody UpdateRealisationHarvestRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        realisationHarvestService.updateRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(realisationFk, fruitCultivarFk, classFk, requestDto),
        "Berba odrađena u realizaciji uspješno promijenjena."
      )
    );
  }

  @DeleteMapping(Mappings.deleteRealisationHarvestByByFruitCultivarIdAndClassIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(@PathVariable Long realisationFk, @PathVariable Integer fruitCultivarFk, @PathVariable Integer classFk) {
    realisationHarvestService.deleteRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(realisationFk, fruitCultivarFk, classFk);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Berba odrađena u realizaciji uspješno obrisana."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = RealisationController.Mappings.requestMapping + "/{realisationFk}/harvests";

    public static final String getAllRealisationHarvestsGetMapping = "";

    public static final String getAllRealisationHarvestsByFruitCultivarIdGetMapping = "/{fruitCultivarFk}";

    public static final String getRealisationHarvestByFruitCultivarIdAndClassIdGetMapping = "/{fruitCultivarFk}/classes/{classFk}";

    public static final String createRealisationHarvestPostMapping = "";

    public static final String updateRealisationHarvestByByFruitCultivarIdAndClassIdPutMapping = "/{fruitCultivarFk}/classes/{classFk}";

    public static final String deleteRealisationHarvestByByFruitCultivarIdAndClassIdDeleteMapping = "/{fruitCultivarFk}/classes/{classFk}";

  }

}