package hr.algebra.fruity.exception;

public class RegistrationTokenExpiredException extends ConflictException {

  public RegistrationTokenExpiredException(final String message) {
    super(message);
  }

  public RegistrationTokenExpiredException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public RegistrationTokenExpiredException(final Throwable cause) {
    super(cause);
  }

}