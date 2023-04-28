package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDto;
import hr.algebra.fruity.service.EmployeeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter implements Converter<CreateWorkEmployeeRequestDto, JoinedCreateWorkEmployeeRequestDto> {

  private final EmployeeService employeeService;

  @Override
  public JoinedCreateWorkEmployeeRequestDto convert(@NonNull CreateWorkEmployeeRequestDto source) {
    return new JoinedCreateWorkEmployeeRequestDto(
      employeeService.getById(source.employeeFk()),
      source.costPerHour(),
      source.note()
    );
  }

}
