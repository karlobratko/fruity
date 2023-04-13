package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.repository.CadastralParcelOwnershipStatusRepository;
import hr.algebra.fruity.service.CadastralParcelOwnershipStatusService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastralParcelOwnershipStatusServiceImpl implements CadastralParcelOwnershipStatusService {

  private final ConversionService conversionService;

  private final CadastralParcelOwnershipStatusRepository cadastralParcelOwnershipStatusRepository;

  @Override
  public List<CadastralParcelOwnershipStatusResponseDto> getAllCadastralParcelOwnershipStatuses() {
    return cadastralParcelOwnershipStatusRepository.findAll().stream()
      .map(cadastralParcelOwnershipStatus -> conversionService.convert(cadastralParcelOwnershipStatus, CadastralParcelOwnershipStatusResponseDto.class))
      .toList();
  }

  @Override
  public CadastralParcelOwnershipStatusResponseDto getCadastralParcelOwnershipStatusById(Integer id) {
    return conversionService.convert(getById(id), CadastralParcelOwnershipStatusResponseDto.class);
  }

  @Override
  public CadastralParcelOwnershipStatus getById(Integer id) {
    val cadastralParcelOwnershipStatus = cadastralParcelOwnershipStatusRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return cadastralParcelOwnershipStatus;
  }

}
