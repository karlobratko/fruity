package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.EmployeeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterRequestDtoValidator implements Validator<RegisterRequestDto> {

  private final EmployeeRepository employeeRepository;

  private final UserRepository userRepository;

  private final EmployeeRoleService employeeRoleService;

  @Override
  public void validate(RegisterRequestDto target) {
    if (userRepository.existsByOib(target.oib()))
      throw new UniquenessViolatedException("OIB već postoji i nije jedinstven.");

    if (employeeRepository.existsByEmailAndRole(target.email(), employeeRoleService.getEmployeeRole(EmployeeRoles.ROLE_MANAGER)))
      throw new UniquenessViolatedException("Email već postoji i nije jedinstven.");

    if (employeeRepository.existsByUsername(target.username()))
      throw new UniquenessViolatedException("Korisničko ime već postoji i nije jedinstveno");
  }

}