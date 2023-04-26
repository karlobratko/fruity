package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateWorkEmployeeRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.WorkEmployee;
import hr.algebra.fruity.repository.EmployeeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEmployeeRequestDtoWithWorkAdapterToWorkEmployeeConverter implements Converter<CreateWorkEmployeeRequestDtoWithWorkAdapter, WorkEmployee> {

  private final EmployeeRepository employeeRepository;

  @Override
  public WorkEmployee convert(@NonNull CreateWorkEmployeeRequestDtoWithWorkAdapter source) {
    return WorkEmployee.builder()
      .work(source.work())
      .employee(employeeRepository.findById(source.dto().employeeFk()).orElseThrow(EntityNotFoundException::new))
      .costPerHour(source.dto().costPerHour())
      .note(source.dto().note())
      .build();
  }

}
