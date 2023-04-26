package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.WorkEmployeeResponseDto;
import hr.algebra.fruity.model.WorkEmployee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkEmployeeToWorkEmployeeDtoConverter implements Converter<WorkEmployee, WorkEmployeeResponseDto> {

  private final EmployeeToEmployeeResponseDtoConverter employeeConverter;

  @Override
  public WorkEmployeeResponseDto convert(@NonNull WorkEmployee source) {
    return new WorkEmployeeResponseDto(
      source.getWork().getId(),
      employeeConverter.convert(source.getEmployee()),
      source.getCostPerHour(),
      source.getNote()
    );
  }

}
