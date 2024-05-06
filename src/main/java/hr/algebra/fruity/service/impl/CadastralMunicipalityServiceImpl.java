package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CadastralMunicipalityToCadastralMunicipalityResponseDtoConverter;
import hr.algebra.fruity.converter.CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter;
import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.service.CadastralMunicipalityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastralMunicipalityServiceImpl implements CadastralMunicipalityService {

  private final CadastralMunicipalityToCadastralMunicipalityResponseDtoConverter toCadastralMunicipalityResponseDtoConverter;

  private final CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter toFullCadastralMunicipalityResponseDtoConverter;

  private final CadastralMunicipalityRepository cadastralMunicipalityRepository;

  @Override
  public List<CadastralMunicipalityResponseDto> getAllCadastralMunicipalities() {
    return cadastralMunicipalityRepository.findAll().stream()
      .map(toCadastralMunicipalityResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullCadastralMunicipalityResponseDto getCadastralMunicipalityById(Integer id) {
    return toFullCadastralMunicipalityResponseDtoConverter.convert(getById(id));
  }

  @Override
  public CadastralMunicipality getById(Integer id) {
    return cadastralMunicipalityRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Katastarska općina"));
  }

}
