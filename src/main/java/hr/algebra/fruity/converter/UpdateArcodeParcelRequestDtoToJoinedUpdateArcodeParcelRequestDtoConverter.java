package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateArcodeParcelRequestDto;
import hr.algebra.fruity.service.CadastralParcelService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateArcodeParcelRequestDtoToJoinedUpdateArcodeParcelRequestDtoConverter implements Converter<UpdateArcodeParcelRequestDto, JoinedUpdateArcodeParcelRequestDto> {

  private final CadastralParcelService cadastralParcelService;

  @Override
  public JoinedUpdateArcodeParcelRequestDto convert(@NonNull UpdateArcodeParcelRequestDto source) {
    return new JoinedUpdateArcodeParcelRequestDto(
      source.name(),
      Objects.nonNull(source.cadastralParcelFk()) ? cadastralParcelService.getById(source.cadastralParcelFk()) : null,
      source.arcode(),
      source.surface()
    );
  }

}
