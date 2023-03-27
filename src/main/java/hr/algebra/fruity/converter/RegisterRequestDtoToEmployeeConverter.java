package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.exception.EmployeeRoleExpectationException;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.EmployeeRoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterRequestDtoToEmployeeConverter implements Converter<RegisterRequestDto, Employee> {

  private final PasswordEncoder passwordEncoder;

  private final EmployeeRoleRepository employeeRoleRepository;

  @Override
  public Employee convert(@NonNull RegisterRequestDto source) {
    return Employee.builder()
      .firstName(source.firstName())
      .lastName(source.lastName())
      .username(source.username())
      .email(source.email())
      .password(passwordEncoder.encode(source.password()))
      .role(employeeRoleRepository.findByName(EmployeeRoles.ROLE_MANAGER.name()).orElseThrow(() -> new EmployeeRoleExpectationException(EmployeeRoles.ROLE_PERFORMER)))
      .build();
  }

}
