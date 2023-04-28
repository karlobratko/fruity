package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateCadastralParcelRequestDto;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateCadastralParcelRequestDtoToCadastralParcelConverter implements Converter<JoinedCreateCadastralParcelRequestDto, CadastralParcel> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public CadastralParcel convert(@NonNull JoinedCreateCadastralParcelRequestDto source) {
    return CadastralParcel.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .cadastralMunicipality(source.cadastralMunicipality())
      .cadastralNumber(source.cadastralNumber())
      .ownershipStatus(source.ownershipStatus())
      .surface(source.surface())
      .build();
  }

}
