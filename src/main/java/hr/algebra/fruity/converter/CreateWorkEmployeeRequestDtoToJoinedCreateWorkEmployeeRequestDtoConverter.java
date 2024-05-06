package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEmployeeRequestDto;
import hr.algebra.fruity.service.EmployeeService;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEmployeeRequestDtoToJoinedCreateWorkEmployeeRequestDtoConverter implements Converter<CreateWorkEmployeeRequestDto, JoinedCreateWorkEmployeeRequestDto> {

  private final EmployeeService employeeService;

  @Override
  public JoinedCreateWorkEmployeeRequestDto convert(@NonNull CreateWorkEmployeeRequestDto source) {
    val employee = employeeService.getById(source.employeeFk());

    return new JoinedCreateWorkEmployeeRequestDto(
      employee,
      Objects.requireNonNullElse(source.costPerHour(), employee.getCostPerHour()),
      source.note()
    );
  }

}
