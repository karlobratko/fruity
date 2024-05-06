package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.CadastralMunicipality;
import java.util.List;

public interface CadastralMunicipalityService {

  List<CadastralMunicipalityResponseDto> getAllCadastralMunicipalities();

  FullCadastralMunicipalityResponseDto getCadastralMunicipalityById(Integer id);

  CadastralMunicipality getById(Integer id);
  
}
