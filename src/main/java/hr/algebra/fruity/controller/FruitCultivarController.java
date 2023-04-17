package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FruitCultivarResponseDto;
import hr.algebra.fruity.service.FruitCultivarService;
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
@RequestMapping(FruitCultivarController.Mappings.requestMapping)
@RequiredArgsConstructor
public class FruitCultivarController {

  private final FruitCultivarService fruitCultivarService;

  @GetMapping(Mappings.getAllFruitCultivarsGetMapping)
  public ResponseEntity<ApiResponse<List<FruitCultivarResponseDto>>> getAllFruitCultivars() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        fruitCultivarService.getAllFruitCultivars(),
        "Sorte voća uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getFruitCultivarByIdGetMapping)
  public ResponseEntity<ApiResponse<FruitCultivarResponseDto>> getFruitCultivarById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        fruitCultivarService.getFruitCultivarById(id),
        "Sorta voća uspješno dohvaćena."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/fruit-cultivars";

    public static final String getAllFruitCultivarsGetMapping = "";

    public static final String getFruitCultivarByIdGetMapping = "/{id}";

  }

}
