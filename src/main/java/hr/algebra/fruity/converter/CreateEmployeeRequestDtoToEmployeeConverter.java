package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateEmployeeRequestDto;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.EmployeeRoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmployeeRequestDtoToEmployeeConverter implements Converter<CreateEmployeeRequestDto, Employee> {

  private final CurrentRequestUserService currentRequestUserService;

  private final EmployeeRoleService employeeRoleService;

  @Override
  public Employee convert(@NonNull CreateEmployeeRequestDto source) {
    return Employee.builder()
      .user(currentRequestUserService.getUser())
      .firstName(source.firstName())
      .lastName(source.lastName())
      .email(source.email())
      .phoneNumber(source.phoneNumber())
      .costPerHour(source.costPerHour())
      .role(employeeRoleService.getEmployeeRole(EmployeeRoles.ROLE_PERFORMER))
      .build();
  }

}
