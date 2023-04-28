package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullRealisationResponseDto;
import hr.algebra.fruity.model.Realisation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationToFullRealisationResponseDtoConverter implements Converter<Realisation, FullRealisationResponseDto> {

  private final WorkToWorkResponseDtoConverter workConverter;

  private final EmployeeToEmployeeResponseDtoConverter employeeConverter;

  @Override
  public FullRealisationResponseDto convert(Realisation source) {
    return new FullRealisationResponseDto(
      source.getId(),
      workConverter.convert(source.getWork()),
      employeeConverter.convert(source.getEmployee()),
      source.getStartDateTime(),
      source.getEndDateTime(),
      source.getNote()
    );
  }

}
