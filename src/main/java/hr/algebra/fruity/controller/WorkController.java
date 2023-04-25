package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateWorkRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullWorkResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.service.WorkService;
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
@RequestMapping(WorkController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkController {

  private final WorkService workService;

  @GetMapping(Mappings.getAllWorksGetMapping)
  public ResponseEntity<ApiResponse<List<WorkResponseDto>>> getAllWork() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.getAllWorks(),
        "Radovi uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getWorkByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkResponseDto>> getWorkById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.getWorkById(id),
        "Rad uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createWorkPostMapping)
  public ResponseEntity<ApiResponse<FullWorkResponseDto>> createWork(@Valid @RequestBody CreateWorkRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.createWork(requestDto),
        "Rad uspješno dodan."
      )
    );
  }

  @PutMapping(Mappings.updateWorkByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkResponseDto>> updateWorkById(@PathVariable Long id, @Valid @RequestBody UpdateWorkRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workService.updateWorkById(id, requestDto),
        "Rad uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkById(@PathVariable Long id) {
    workService.deleteWorkById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Rad uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiWorkManagementRequestMapping + "/works";

    public static final String getAllWorksGetMapping = "";

    public static final String getWorkByIdGetMapping = "/{id}";

    public static final String createWorkPostMapping = "";

    public static final String updateWorkByIdPutMapping = "/{id}";

    public static final String deleteWorkByIdDeleteMapping = "/{id}";

  }

}