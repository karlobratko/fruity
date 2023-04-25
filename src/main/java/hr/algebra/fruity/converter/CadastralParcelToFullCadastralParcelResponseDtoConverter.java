package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullCadastralParcelResponseDto;
import hr.algebra.fruity.model.CadastralParcel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastralParcelToFullCadastralParcelResponseDtoConverter implements Converter<CadastralParcel, FullCadastralParcelResponseDto> {

  private final CadastralMunicipalityToFullCadastralMunicipalityResponseDtoConverter cadastralMunicipalityConverter;

  private final CadastralParcelOwnershipStatusToCadastralParcelOwnershipStatusResponseDto cadastralParcelOwnershipStatusConverter;

  @Override
  public FullCadastralParcelResponseDto convert(@NonNull CadastralParcel source) {
    return new FullCadastralParcelResponseDto(
      source.getId(),
      source.getName(),
      cadastralMunicipalityConverter.convert(source.getCadastralMunicipality()),
      source.getCadastralNumber(),
      source.getSurface(),
      cadastralParcelOwnershipStatusConverter.convert(source.getOwnershipStatus())
    );
  }

}
