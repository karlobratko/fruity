package hr.algebra.fruity.exception;

import hr.algebra.fruity.model.codebook.EmployeeRoles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class EmployeeRoleExpectationException extends InternalServerErrorException {

  public EmployeeRoleExpectationException(EmployeeRoles role) {
    super(Constants.exceptionMessageFormat.formatted(role.displayName()));
  }

  public EmployeeRoleExpectationException(EmployeeRoles role, Throwable cause) {
    super(Constants.exceptionMessageFormat.formatted(role.displayName()), cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Uloga %s nije pronađena.";

  }

}
