package hr.algebra.fruity.controller;

import hr.algebra.fruity.constants.ApplicationConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CadastralParcelController.Mappings.requestMapping)
@RequiredArgsConstructor
public class CadastralParcelController {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Mappings {

    public static final String requestMapping = ApplicationConstants.apiInventoryManagementRequestMapping + "/cadastral-parcels";

  }

}
