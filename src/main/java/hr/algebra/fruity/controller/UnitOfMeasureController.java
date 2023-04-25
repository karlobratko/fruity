package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullUnitOfMeasureResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import hr.algebra.fruity.service.UnitOfMeasureService;
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
@RequestMapping(UnitOfMeasureController.Mappings.requestMapping)
@RequiredArgsConstructor
public class UnitOfMeasureController {

  private final UnitOfMeasureService agentService;

  @GetMapping(Mappings.getAllUnitsOfMeasureGetMapping)
  public ResponseEntity<ApiResponse<List<UnitOfMeasureResponseDto>>> getAllUnitsOfMeasure() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        agentService.getAllUnitsOfMeasure(),
        "Jedinice mjere uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getUnitOfMeasureByIdGetMapping)
  public ResponseEntity<ApiResponse<FullUnitOfMeasureResponseDto>> getUnitOfMeasureById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        agentService.getUnitOfMeasureById(id),
        "Jedinica mjere uspješno dohvaćena."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiResourceManagementRequestMapping + "/units-of-measure";

    public static final String getAllUnitsOfMeasureGetMapping = "";

    public static final String getUnitOfMeasureByIdGetMapping = "/{id}";

  }

}
