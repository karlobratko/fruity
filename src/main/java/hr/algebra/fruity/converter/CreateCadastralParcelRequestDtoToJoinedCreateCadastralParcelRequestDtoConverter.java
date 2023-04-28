package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateCadastralParcelRequestDto;
import hr.algebra.fruity.service.CadastralMunicipalityService;
import hr.algebra.fruity.service.CadastralParcelOwnershipStatusService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCadastralParcelRequestDtoToJoinedCreateCadastralParcelRequestDtoConverter implements Converter<CreateCadastralParcelRequestDto, JoinedCreateCadastralParcelRequestDto> {

  private final CadastralMunicipalityService cadastralMunicipalityService;

  private final CadastralParcelOwnershipStatusService cadastralParcelOwnershipStatusService;

  @Override
  public JoinedCreateCadastralParcelRequestDto convert(@NonNull CreateCadastralParcelRequestDto source) {
    return new JoinedCreateCadastralParcelRequestDto(
      source.name(),
      cadastralMunicipalityService.getById(source.cadastralMunicipalityFk()),
      source.cadastralNumber(),
      source.surface(),
      cadastralParcelOwnershipStatusService.getById(source.ownershipStatusFk())
    );
  }

}
