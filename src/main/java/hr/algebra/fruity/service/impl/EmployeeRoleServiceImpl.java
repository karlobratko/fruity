package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.exception.EmployeeRoleExpectationException;
import hr.algebra.fruity.model.EmployeeRole;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.EmployeeRoleRepository;
import hr.algebra.fruity.service.EmployeeRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

  private final EmployeeRoleRepository employeeRoleRepository;

  @Override
  public EmployeeRole getEmployeeRole(EmployeeRoles employeeRole) {
    return employeeRoleRepository.findByName(employeeRole.name())
      .orElseThrow(() -> new EmployeeRoleExpectationException(employeeRole));
  }

}
