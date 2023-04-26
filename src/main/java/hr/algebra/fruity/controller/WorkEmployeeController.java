package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.UpdateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.FullWorkEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkEmployeeResponseDto;
import hr.algebra.fruity.service.WorkEmployeeService;
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
@RequestMapping(WorkEmployeeController.Mappings.requestMapping)
@RequiredArgsConstructor
public class WorkEmployeeController {

  private final WorkEmployeeService workEmployees;

  @GetMapping(Mappings.getAllWorkEmployeesGetMapping)
  public ResponseEntity<ApiResponse<List<WorkEmployeeResponseDto>>> getAllWorkEmployeesByWorkId(@PathVariable Long workFk) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEmployees.getAllWorkEmployeesByWorkId(workFk),
        "Zaposlenici koji sudjeluju u radu uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getWorkEmployeeByIdGetMapping)
  public ResponseEntity<ApiResponse<FullWorkEmployeeResponseDto>> getWorkEmployeeByWorkIdAndEmployeeId(@PathVariable Long workFk, @PathVariable Long employeeId) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEmployees.getWorkEmployeeByWorkIdAndEmployeeId(workFk, employeeId),
        "Zaposlenik koji sudjeluje u radu uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createWorkEmployeePostMapping)
  public ResponseEntity<ApiResponse<FullWorkEmployeeResponseDto>> createWorkEmployeeForWorkId(@PathVariable Long workFk, @Valid @RequestBody CreateWorkEmployeeRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEmployees.createWorkEmployeeForWorkId(workFk, requestDto),
        "Zaposlenik uspješno dodan u rad."
      )
    );
  }

  @PutMapping(Mappings.updateWorkEmployeeByIdPutMapping)
  public ResponseEntity<ApiResponse<FullWorkEmployeeResponseDto>> updateWorkEmployeeByWorkIdAndEmployeeId(@PathVariable Long workFk, @PathVariable Long employeeId, @Valid @RequestBody UpdateWorkEmployeeRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        workEmployees.updateWorkEmployeeByWorkIdAndEmployeeId(workFk, employeeId, requestDto),
        "Zaposlenik koji sudjeluje u radu uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteWorkEmployeeByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteWorkEmployeeByWorkIdAndEmployeeId(@PathVariable Long workFk, @PathVariable Long employeeId) {
    workEmployees.deleteWorkEmployeeByWorkIdAndEmployeeId(workFk, employeeId);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Zaposlenik koji sudjeluje u radu uspješno obrisan."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = WorkController.Mappings.requestMapping + "/{workFk}/employees";

    public static final String getAllWorkEmployeesGetMapping = "";

    public static final String getWorkEmployeeByIdGetMapping = "/{employeeId}";

    public static final String createWorkEmployeePostMapping = "";

    public static final String updateWorkEmployeeByIdPutMapping = "/{employeeId}";

    public static final String deleteWorkEmployeeByIdDeleteMapping = "/{employeeId}";

  }

}