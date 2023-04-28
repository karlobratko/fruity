package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RealisationResponseDto;
import hr.algebra.fruity.model.Realisation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealisationToRealisationResponseDtoConverter implements Converter<Realisation, RealisationResponseDto> {

  private final EmployeeToEmployeeResponseDtoConverter employeeConverter;

  @Override
  public RealisationResponseDto convert(Realisation source) {
    return new RealisationResponseDto(
      source.getId(),
      source.getWork().getId(),
      employeeConverter.convert(source.getEmployee()),
      source.getStartDateTime(),
      source.getEndDateTime()
    );
  }

}
