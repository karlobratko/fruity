package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.HarvestedFruitClassResponseDto;
import hr.algebra.fruity.service.HarvestedFruitClassService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HarvestedFruitClassController.Mappings.requestMapping)
@RequiredArgsConstructor
public class HarvestedFruitClassController {

  private final HarvestedFruitClassService harvestedFruitClassService;

  @GetMapping(Mappings.getAllHarvestedFruitClassesGetMapping)
  public ResponseEntity<ApiResponse<List<HarvestedFruitClassResponseDto>>> getAllHarvestedFruitClasses() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        harvestedFruitClassService.getAllHarvestedFruitClasses(),
        "Klase voća uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getHarvestedFruitClassByIdGetMapping)
  public ResponseEntity<ApiResponse<HarvestedFruitClassResponseDto>> getHarvestedFruitClassById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        harvestedFruitClassService.getHarvestedFruitClassById(id),
        "Klasa voća uspješno dohvaćena."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = RealisationController.Mappings.requestMapping + "/harvests/classes";

    public static final String getAllHarvestedFruitClassesGetMapping = "";

    public static final String getHarvestedFruitClassByIdGetMapping = "/{id}";

  }

}
