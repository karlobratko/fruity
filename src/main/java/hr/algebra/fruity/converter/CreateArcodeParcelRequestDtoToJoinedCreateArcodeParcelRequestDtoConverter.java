package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateArcodeParcelRequestDto;
import hr.algebra.fruity.service.CadastralParcelService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateArcodeParcelRequestDtoToJoinedCreateArcodeParcelRequestDtoConverter implements Converter<CreateArcodeParcelRequestDto, JoinedCreateArcodeParcelRequestDto> {

  private final CadastralParcelService cadastralParcelService;

  @Override
  public JoinedCreateArcodeParcelRequestDto convert(@NonNull CreateArcodeParcelRequestDto source) {
    return new JoinedCreateArcodeParcelRequestDto(
      source.name(),
      cadastralParcelService.getById(source.cadastralParcelFk()),
      source.arcode(),
      source.surface()
    );
  }

}
