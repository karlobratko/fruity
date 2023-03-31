package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class InvalidMobileTokenException extends NotFoundException {

  public InvalidMobileTokenException() {
    super(Constants.exceptionMessageFormat);
  }

  public InvalidMobileTokenException(Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Token za pristup mobilnoj aplikaciji nije važeći.";

  }

}
