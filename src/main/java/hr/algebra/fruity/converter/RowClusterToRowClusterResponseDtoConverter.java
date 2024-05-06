package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RowClusterResponseDto;
import hr.algebra.fruity.model.RowCluster;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RowClusterToRowClusterResponseDtoConverter implements Converter<RowCluster, RowClusterResponseDto> {

  @Override
  public RowClusterResponseDto convert(@NonNull RowCluster source) {
    return new RowClusterResponseDto(
      source.getId(),
      source.getName(),
      source.getArcodeParcel().getId(),
      source.getSurface()
    );
  }

}
