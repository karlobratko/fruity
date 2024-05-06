package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CadastralParcelOwnershipStatusToCadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.repository.CadastralParcelOwnershipStatusRepository;
import hr.algebra.fruity.service.CadastralParcelOwnershipStatusService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastralParcelOwnershipStatusServiceImpl implements CadastralParcelOwnershipStatusService {

  private final CadastralParcelOwnershipStatusToCadastralParcelOwnershipStatusResponseDto toCadastralParcelOwnershipStatusResponseDto;

  private final CadastralParcelOwnershipStatusRepository cadastralParcelOwnershipStatusRepository;

  @Override
  public List<CadastralParcelOwnershipStatusResponseDto> getAllCadastralParcelOwnershipStatuses() {
    return cadastralParcelOwnershipStatusRepository.findAll().stream()
      .map(toCadastralParcelOwnershipStatusResponseDto::convert)
      .toList();
  }

  @Override
  public CadastralParcelOwnershipStatusResponseDto getCadastralParcelOwnershipStatusById(Integer id) {
    return toCadastralParcelOwnershipStatusResponseDto.convert(getById(id));
  }

  @Override
  public CadastralParcelOwnershipStatus getById(Integer id) {
    return cadastralParcelOwnershipStatusRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Status katastarske čestice"));
  }

}
