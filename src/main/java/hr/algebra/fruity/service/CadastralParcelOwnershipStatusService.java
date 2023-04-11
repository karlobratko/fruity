package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import java.util.List;

public interface CadastralParcelOwnershipStatusService {

  List<CadastralParcelOwnershipStatusResponseDto> getAllCadastralParcelOwnershipStatuses();

  CadastralParcelOwnershipStatusResponseDto getCadastralParcelOwnershipStatusById(Integer id);

}
