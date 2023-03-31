package hr.algebra.fruity.service;

import hr.algebra.fruity.exception.EmployeeRoleExpectationException;
import hr.algebra.fruity.model.codebook.EmployeeRoles;
import hr.algebra.fruity.repository.EmployeeRoleRepository;
import hr.algebra.fruity.service.impl.EmployeeRoleServiceImpl;
import hr.algebra.fruity.utils.mother.model.EmployeeRoleMother;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeRoleService Unit Test")
@SuppressWarnings("static-access")
public class EmployeeRoleServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private EmployeeRoleServiceImpl employeeRoleService;

  @Mock
  private EmployeeRoleRepository employeeRoleRepository;

  @Nested
  @DisplayName("... WHEN getEmployeeRole is called")
  class WHEN_getEmployeeRole {

    @Test
    @DisplayName("GIVEN EmployeeRoles " +
      "... THEN EmployeeRoleExpectationException is thrown")
    public void GIVEN_EmployeeRoles_THEN_EmployeeRoleExpectationException() {
      // GIVEN
      // ... EmployeeRoles
      val employeeRoles = EmployeeRoles.ROLE_MANAGER;
      // ... EmployeeRoleRepository will fail to find EmployeeRole by name
      given(employeeRoleRepository.findByName(same(employeeRoles.name()))).willReturn(Optional.empty());

      // WHEN
      // ... getEmployeeRole is called
      when(() -> employeeRoleService.getEmployeeRole(employeeRoles));

      // THEN
      // ... EmployeeRole
      and.then(caughtException())
        .isInstanceOf(EmployeeRoleExpectationException.class)
        .hasMessage(EmployeeRoleExpectationException.Constants.exceptionMessageFormat.formatted(employeeRoles.displayName()))
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN EmployeeRoles " +
      "... THEN EmployeeRole is returned")
    public void GIVEN_EmployeeRoles_THEN_EmployeeRole() {
      // GIVEN
      // ... EmployeeRoles
      val employeeRoles = EmployeeRoles.ROLE_MANAGER;
      // ... EmployeeRoleRepository will successfully find EmployeeRole by name
      val expectedEmployeeRole = EmployeeRoleMother.complete().build();
      given(employeeRoleRepository.findByName(same(employeeRoles.name()))).willReturn(Optional.of(expectedEmployeeRole));

      // WHEN
      // ... getEmployeeRole is called
      val employeeRole = employeeRoleService.getEmployeeRole(employeeRoles);

      // THEN
      // ... EmployeeRole
      and.then(employeeRole)
        .isNotNull()
        .isEqualTo(expectedEmployeeRole);
    }

  }

}
