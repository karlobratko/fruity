package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullArcodeParcelResponseDto;
import hr.algebra.fruity.model.ArcodeParcel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArcodeParcelToFullArcodeParcelResponseDtoConverter implements Converter<ArcodeParcel, FullArcodeParcelResponseDto> {

  private final CadastralParcelToCadastralParcelResponseDtoConverter cadastralParcelConverter;

  @Override
  public FullArcodeParcelResponseDto convert(@NonNull ArcodeParcel source) {
    return new FullArcodeParcelResponseDto(
      source.getId(),
      source.getName(),
      cadastralParcelConverter.convert(source.getCadastralParcel()),
      source.getArcode(),
      source.getSurface()
    );
  }

}
