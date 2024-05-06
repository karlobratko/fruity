package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.ArcodeParcelResponseDto;
import hr.algebra.fruity.model.ArcodeParcel;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArcodeParcelToArcodeParcelResponseDtoConverter implements Converter<ArcodeParcel, ArcodeParcelResponseDto> {

  @Override
  public ArcodeParcelResponseDto convert(@NonNull ArcodeParcel source) {
    return new ArcodeParcelResponseDto(
      source.getId(),
      source.getName(),
      source.getCadastralParcel().getId(),
      source.getArcode(),
      source.getSurface()
    );
  }

}
