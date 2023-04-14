package hr.algebra.fruity.controller;

import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.service.CadastralParcelOwnershipStatusService;
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
@RequestMapping(CadastralParcelOwnershipStatusController.Mappings.requestMapping)
@RequiredArgsConstructor
public class CadastralParcelOwnershipStatusController {

  private final CadastralParcelOwnershipStatusService cadastralParcelOwnershipStatusService;

  @GetMapping(Mappings.getAllCountiesGetMapping)
  public ResponseEntity<ApiResponse<List<CadastralParcelOwnershipStatusResponseDto>>> getAllCounties() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralParcelOwnershipStatusService.getAllCadastralParcelOwnershipStatuses(),
        "Statusi katastarskih čestica uspješno dohvaćeni."
      )
    );
  }

  @GetMapping(Mappings.getCadastralParcelOwnershipStatusByIdGetMapping)
  public ResponseEntity<ApiResponse<CadastralParcelOwnershipStatusResponseDto>> getCadastralParcelOwnershipStatusById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralParcelOwnershipStatusService.getCadastralParcelOwnershipStatusById(id),
        "Status katastarske čestice uspješno dohvaćen."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = CadastralParcelController.Mappings.requestMapping + "/ownership-statuses";

    public static final String getAllCountiesGetMapping = "";

    public static final String getCadastralParcelOwnershipStatusByIdGetMapping = "/{id}";

  }

}
