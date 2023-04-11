package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import java.util.List;

public interface CadastralMunicipalityService {

  List<CadastralMunicipalityResponseDto> getAllCadastralMunicipalities();

  FullCadastralMunicipalityResponseDto getCadastralMunicipalityById(Integer id);
  
}
