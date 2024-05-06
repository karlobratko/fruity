package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullWorkEmployeeResponseDto;
import hr.algebra.fruity.model.WorkEmployee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkEmployeeToFullWorkEmployeeResponseDtoConverter implements Converter<WorkEmployee, FullWorkEmployeeResponseDto> {

  private final WorkToWorkResponseDtoConverter workConverter;

  private final EmployeeToEmployeeResponseDtoConverter employeeConverter;

  @Override
  public FullWorkEmployeeResponseDto convert(@NonNull WorkEmployee source) {
    return new FullWorkEmployeeResponseDto(
      workConverter.convert(source.getWork()),
      employeeConverter.convert(source.getEmployee()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
