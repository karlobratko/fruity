package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.CadastralMunicipalityResponseDto;
import hr.algebra.fruity.model.CadastralMunicipality;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CadastralMunicipalityToCadastralMunicipalityResponseDtoConverter implements Converter<CadastralMunicipality, CadastralMunicipalityResponseDto> {

  @Override
  public CadastralMunicipalityResponseDto convert(@NonNull CadastralMunicipality source) {
    return new CadastralMunicipalityResponseDto(
      source.getId(),
      source.getRegistrationNumber(),
      source.getName()
    );
  }

}
