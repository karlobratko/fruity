package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateArcodeParcelRequestDto;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateArcodeParcelRequestDtoToArcodeParcelConverter implements Converter<JoinedCreateArcodeParcelRequestDto, ArcodeParcel> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public ArcodeParcel convert(@NonNull JoinedCreateArcodeParcelRequestDto source) {
    return ArcodeParcel.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .arcode(source.arcode())
      .cadastralParcel(source.cadastralParcel())
      .surface(source.surface())
      .build();
  }

}
