package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.service.EmployeeRoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterRequestDtoToEmployeeConverter implements Converter<RegisterRequestDto, Employee> {

  private final PasswordEncoder passwordEncoder;

  private final EmployeeRoleService employeeRoleService;

  @Override
  public Employee convert(@NonNull RegisterRequestDto source) {
    return Employee.builder()
      .firstName(source.firstName())
      .lastName(source.lastName())
      .username(source.username())
      .email(source.email())
      .password(passwordEncoder.encode(source.password()))
      .role(employeeRoleService.getEmployeeRole(EmployeeRoles.ROLE_MANAGER))
      .build();
  }

}
