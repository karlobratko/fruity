package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class RegistrationTokenExpiredException extends ConflictException {

  public RegistrationTokenExpiredException() {
    super(Constants.exceptionMessageFormat);
  }

  public RegistrationTokenExpiredException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Registracijski token je isteknuo.";

  }

}