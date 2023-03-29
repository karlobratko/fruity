package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class RefreshTokenExpiredException extends ForbiddenException {

  public RefreshTokenExpiredException() {
    super(Constants.exceptionMessageFormat);
  }

  public RefreshTokenExpiredException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Token za osvježivanje je isteknuo. Potrebna je ponovna prijava.";

  }

}