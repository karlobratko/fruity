package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class InvalidTimePointsException extends BadRequestException {

  public InvalidTimePointsException() {
    super(Constants.exceptionMessageFormat);
  }

  public InvalidTimePointsException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Nevažeći početak i završetak aktivnosti, datumi se preklapaju ili su u neispravnom redoslijedu.";

  }

}