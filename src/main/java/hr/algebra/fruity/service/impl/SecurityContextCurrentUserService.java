package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.exception.UsernameNotFoundException;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityContextCurrentUserService implements CurrentUserService {

  private final UserRepository userRepository;

  private final EmployeeRepository employeeRepository;

  @Override
  public User getLoggedInUser() {
    return getLoggedInEmployee().getUser();
  }

  @Override
  public Employee getLoggedInEmployee() {
    val username = SecurityContextHolder.getContext().getAuthentication().getName();

    return employeeRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));
  }

}
