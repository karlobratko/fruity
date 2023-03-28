package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import hr.algebra.fruity.dto.response.ApiResponse;
import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.service.CountyService;
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
@RequestMapping(CountyController.Mappings.requestMapping)
@RequiredArgsConstructor
public class CountyController {

  private final CountyService countyService;

  @GetMapping(Mappings.getAllCountiesGetMapping)
  public ResponseEntity<ApiResponse<List<CountyResponseDto>>> getAllCounties() {
    return ResponseEntity.ok(
      ApiResponse.ok(
        countyService.getAllCounties(),
        "Sve županije uspješno dohvaćene."
      )
    );
  }

  @GetMapping(Mappings.getCountyByIdGetMapping)
  public ResponseEntity<ApiResponse<CountyResponseDto>> getCountyById(@PathVariable Integer id) {
    return ResponseEntity.ok(
      ApiResponse.ok(
        countyService.getCountyById(id),
        "Županija uspješno dohvaćena."
      )
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiUserManagementRequestMapping + "/counties";

    public static final String getAllCountiesGetMapping = "";

    public static final String getCountyByIdGetMapping = "/{id}";

  }

}
