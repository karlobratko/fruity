package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class InvalidRegistrationTokenException extends NotFoundException {

  public InvalidRegistrationTokenException() {
    super(Constants.exceptionMessageFormat);
  }

  public InvalidRegistrationTokenException(Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Registracijski token nije važeći.";

  }

}
