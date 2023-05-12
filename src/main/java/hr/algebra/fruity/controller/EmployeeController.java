package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.request.CreateEmployeeRequestDto;
import hr.algebra.fruity.dto.request.UpdateEmployeeRequestDto;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.EmployeeResponseDto;
import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.dto.response.WorkResponseDto;
import hr.algebra.fruity.service.EmployeeService;
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
@RequestMapping(EmployeeController.Mappings.requestMapping)
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping(Mappings.getAllEmployeesGetMapping)
  public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> getAllEmployees() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        employeeService.getAllEmployees(),
        "Zaposlenici uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getEmployeeByIdGetMapping)
  public ResponseEntity<ApiResponse<FullEmployeeResponseDto>> getEmployeeById(@PathVariable Long id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        employeeService.getEmployeeById(id),
        "Zaposlenik uspješno dohvaćen."
      )
    );
  }

  @PostMapping(Mappings.createEmployeePostMapping)
  public ResponseEntity<ApiResponse<FullEmployeeResponseDto>> createEmployee(@Valid @RequestBody CreateEmployeeRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        employeeService.createEmployee(requestDto),
        "Zaposlenik uspješno dodan."
      )
    );
  }

  @PutMapping(Mappings.updateEmployeeByIdPutMapping)
  public ResponseEntity<ApiResponse<FullEmployeeResponseDto>> updateEmployeeById(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequestDto requestDto) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        employeeService.updateEmployeeById(id, requestDto),
        "Zaposlenik uspješno promijenjen."
      )
    );
  }

  @DeleteMapping(Mappings.deleteEmployeeByIdDeleteMapping)
  public ResponseEntity<ApiResponse<?>> deleteEmployeeById(@PathVariable Long id) {
    employeeService.deleteEmployeeById(id);
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          "Zaposlenik uspješno obrisan."
        )
      );
  }

  @GetMapping(Mappings.getAllAssignedWorksGetMapping)
  public ResponseEntity<ApiResponse<List<WorkResponseDto>>> getAllAssignedWorks() {
    return ResponseEntity
      .ok(
        ApiResponse.ok(
          employeeService.getAllAssignedWorks(),
          "Radovi dodjeljeni radniku uspješno dohvaćeni."
        )
      );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiResourceManagementRequestMapping + "/employees";

    public static final String getAllEmployeesGetMapping = "";

    public static final String getEmployeeByIdGetMapping = "/{id}";

    public static final String createEmployeePostMapping = "";

    public static final String updateEmployeeByIdPutMapping = "/{id}";

    public static final String deleteEmployeeByIdDeleteMapping = "/{id}";

    public static final String getAllAssignedWorksGetMapping = "/works";

  }

}
