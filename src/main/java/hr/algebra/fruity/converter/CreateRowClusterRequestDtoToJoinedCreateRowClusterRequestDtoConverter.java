package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRowClusterRequestDto;
import hr.algebra.fruity.service.ArcodeParcelService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRowClusterRequestDtoToJoinedCreateRowClusterRequestDtoConverter implements Converter<CreateRowClusterRequestDto, JoinedCreateRowClusterRequestDto> {

  private final ArcodeParcelService arcodeParcelService;

  @Override
  public JoinedCreateRowClusterRequestDto convert(@NonNull CreateRowClusterRequestDto source) {
    return new JoinedCreateRowClusterRequestDto(
      source.name(),
      arcodeParcelService.getById(source.arcodeParcelFk()),
      source.surface()
    );
  }

}
