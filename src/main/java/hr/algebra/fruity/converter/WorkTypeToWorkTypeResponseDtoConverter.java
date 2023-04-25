package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkTypeResponseDto;
import hr.algebra.fruity.model.WorkType;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkTypeToWorkTypeResponseDtoConverter implements Converter<WorkType, WorkTypeResponseDto> {

  @Override
  public WorkTypeResponseDto convert(@NonNull WorkType source) {
    return new WorkTypeResponseDto(
      source.getId(),
      source.getDisplayName()
    );
  }

}
