package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import hr.algebra.fruity.service.WorkTypeService;
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
@RequestMapping(WorkTypeController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkTypeController {

  private final WorkTypeService workTypeService;

  @GetMapping(Mappings.getAllWorkTypesGetMapping)
  public ResponseEntity<ApiResponse<List<WorkTypeResponseDto>>> getAllWorkTypes() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workTypeService.getAllWorkTypes(),
        "Vrste rada uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getWorkTypeByIdGetMapping)
  public ResponseEntity<ApiResponse<WorkTypeResponseDto>> getWorkTypeById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workTypeService.getWorkTypeById(id),
        "Vrsta rada uspješno dohvaćena."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiWorkManagementRequestMapping + "/work-types";

    public static final String getAllWorkTypesGetMapping = "";

    public static final String getWorkTypeByIdGetMapping = "/{id}";

  }

}
