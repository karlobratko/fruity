package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import java.util.List;

public interface CadastralParcelOwnershipStatusService {

  List<CadastralParcelOwnershipStatusResponseDto> getAllCadastralParcelOwnershipStatuses();

  CadastralParcelOwnershipStatusResponseDto getCadastralParcelOwnershipStatusById(Integer id);

  CadastralParcelOwnershipStatus getById(Integer id);

}
