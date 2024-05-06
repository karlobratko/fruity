package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkTypeResponseDto;
import hr.algebra.fruity.model.WorkType;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkTypeToFullWorkTypeResponseDtoConverter implements Converter<WorkType, FullWorkTypeResponseDto> {

  @Override
  public FullWorkTypeResponseDto convert(@NonNull WorkType source) {
    return new FullWorkTypeResponseDto(
      source.getId(),
      source.getDisplayName(),
      source.getWorkersTab(),
      source.getRowsTab(),
      source.getEquipmentsTab(),
      source.getAttachmentsTab(),
      source.getAgentsTab(),
      source.getQuantitiesTab()
    );
  }

}
