package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateCadastralParcelRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.repository.CadastralParcelOwnershipStatusRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCadastralParcelRequestDtoToCadastralParcelConverter implements Converter<CreateCadastralParcelRequestDto, CadastralParcel> {

  private final CurrentRequestUserService currentRequestUserService;

  private final CadastralMunicipalityRepository cadastralMunicipalityRepository;

  private final CadastralParcelOwnershipStatusRepository cadastralParcelOwnershipStatusRepository;

  @Override
  public CadastralParcel convert(@NonNull CreateCadastralParcelRequestDto source) {
    return CadastralParcel.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .cadastralMunicipality(cadastralMunicipalityRepository.findById(source.cadastralMunicipalityFk()).orElseThrow(EntityNotFoundException::new))
      .cadastralNumber(source.cadastralNumber())
      .ownershipStatus(cadastralParcelOwnershipStatusRepository.findById(source.ownershipStatusFk()).orElseThrow(EntityNotFoundException::new))
      .surface(source.surface())
      .build();
  }

}
