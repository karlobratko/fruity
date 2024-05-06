package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.service.CadastralMunicipalityService;
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
@RequestMapping(CadastralMunicipalityController.Mappings.requestMapping)
@RequiredArgsConstructor
public class CadastralMunicipalityController {

  private final CadastralMunicipalityService cadastralMunicipalityService;

  @GetMapping(Mappings.getAllCountiesGetMapping)
  public ResponseEntity<ApiResponse<List<CadastralMunicipalityResponseDto>>> getAllCounties() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralMunicipalityService.getAllCadastralMunicipalities(),
        "Katastarske općine uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getCadastralMunicipalityByIdGetMapping)
  public ResponseEntity<ApiResponse<FullCadastralMunicipalityResponseDto>> getCadastralMunicipalityById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        cadastralMunicipalityService.getCadastralMunicipalityById(id),
        "Katastarska općina uspješno dohvaćena."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/cadastral-municipalities";

    public static final String getAllCountiesGetMapping = "";

    public static final String getCadastralMunicipalityByIdGetMapping = "/{id}";

  }

}
