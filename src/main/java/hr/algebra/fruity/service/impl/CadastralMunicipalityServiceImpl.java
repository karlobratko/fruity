package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.service.CadastralMunicipalityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastralMunicipalityServiceImpl implements CadastralMunicipalityService {

  private final ConversionService conversionService;

  private final CadastralMunicipalityRepository cadastralMunicipalityRepository;

  @Override
  public List<CadastralMunicipalityResponseDto> getAllCadastralMunicipalities() {
    return cadastralMunicipalityRepository.findAll().stream()
      .map(cadastralMunicipality -> conversionService.convert(cadastralMunicipality, CadastralMunicipalityResponseDto.class))
      .toList();
  }

  @Override
  public FullCadastralMunicipalityResponseDto getCadastralMunicipalityById(Integer id) {
    return conversionService.convert(getCadastralMunicipality(id), FullCadastralMunicipalityResponseDto.class);
  }

  private CadastralMunicipality getCadastralMunicipality(Integer id) {
    val cadastralMunicipality = cadastralMunicipalityRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return cadastralMunicipality;
  }

}
