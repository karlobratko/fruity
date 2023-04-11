package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.CadastralParcelResponseDto;
import hr.algebra.fruity.model.CadastralParcel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastralParcelToCadastralParcelResponseDtoConverter implements Converter<CadastralParcel, CadastralParcelResponseDto> {

  private final CadastralMunicipalityToCadastralMunicipalityResponseDtoConverter cadastralMunicipalityToCadastralMunicipalityResponseDtoConverter;

  @Override
  public CadastralParcelResponseDto convert(@NonNull CadastralParcel source) {
    return new CadastralParcelResponseDto(
      source.getId(),
      source.getName(),
      cadastralMunicipalityToCadastralMunicipalityResponseDtoConverter.convert(source.getCadastralMunicipality()),
      source.getCadastralNumber(),
      source.getSurface(),
      source.getOwnershipStatus().getDisplayName()
    );
  }

}
