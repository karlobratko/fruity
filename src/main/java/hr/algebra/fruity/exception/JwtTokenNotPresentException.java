package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class JwtTokenNotPresentException extends UnauthorizedException {

  public JwtTokenNotPresentException() {
    super(Constants.exceptionMessageFormat);
  }

  public JwtTokenNotPresentException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "JWT token nije dostavljen.";

  }

}
