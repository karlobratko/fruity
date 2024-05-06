package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRowClusterResponseDto;
import hr.algebra.fruity.model.RowCluster;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RowClusterToFullRowClusterResponseDtoConverter implements Converter<RowCluster, FullRowClusterResponseDto> {

  private final ArcodeParcelToArcodeParcelResponseDtoConverter arcodeParcelConverter;

  @Override
  public FullRowClusterResponseDto convert(@NonNull RowCluster source) {
    return new FullRowClusterResponseDto(
      source.getId(),
      source.getName(),
      arcodeParcelConverter.convert(source.getArcodeParcel()),
      source.getSurface()
    );
  }

}
