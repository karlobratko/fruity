package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullCadastralMunicipalityResponseDto;
import hr.algebra.fruity.model.CadastralMunicipality;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter implements Converter<CadastralMunicipality, FullCadastralMunicipalityResponseDto> {

  @Override
  public FullCadastralMunicipalityResponseDto convert(@NonNull CadastralMunicipality source) {
    return new FullCadastralMunicipalityResponseDto(
      source.getId(),
      source.getRegistrationNumber(),
      source.getName(),
      source.getDepartment(),
      source.getRegionalCadastreOffice()
    );
  }

}
