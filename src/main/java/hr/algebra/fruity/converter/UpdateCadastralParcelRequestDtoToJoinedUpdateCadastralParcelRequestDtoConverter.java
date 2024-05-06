package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateCadastralParcelRequestDto;
import hr.algebra.fruity.service.CadastralMunicipalityService;
import hr.algebra.fruity.service.CadastralParcelOwnershipStatusService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCadastralParcelRequestDtoToJoinedUpdateCadastralParcelRequestDtoConverter implements Converter<UpdateCadastralParcelRequestDto, JoinedUpdateCadastralParcelRequestDto> {

  private final CadastralMunicipalityService cadastralMunicipalityService;

  private final CadastralParcelOwnershipStatusService cadastralParcelOwnershipStatusService;

  @Override
  public JoinedUpdateCadastralParcelRequestDto convert(@NonNull UpdateCadastralParcelRequestDto source) {
    return new JoinedUpdateCadastralParcelRequestDto(
      source.name(),
      Objects.nonNull(source.cadastralMunicipalityFk()) ? cadastralMunicipalityService.getById(source.cadastralMunicipalityFk()) : null,
      source.cadastralNumber(),
      source.surface(),
      Objects.nonNull(source.ownershipStatusFk()) ? cadastralParcelOwnershipStatusService.getById(source.ownershipStatusFk()) : null
    );
  }

}
