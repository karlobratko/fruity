package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedUpdateRowClusterRequestDto;
import hr.algebra.fruity.service.ArcodeParcelService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRowClusterRequestDtoToJoinedUpdateRowClusterRequestDtoConverter implements Converter<UpdateRowClusterRequestDto, JoinedUpdateRowClusterRequestDto> {

  private final ArcodeParcelService arcodeParcelService;

  @Override
  public JoinedUpdateRowClusterRequestDto convert(@NonNull UpdateRowClusterRequestDto source) {
    return new JoinedUpdateRowClusterRequestDto(
      source.name(),
      Objects.nonNull(source.arcodeParcelFk()) ? arcodeParcelService.getById(source.arcodeParcelFk()) : null,
      source.surface()
    );
  }

}
