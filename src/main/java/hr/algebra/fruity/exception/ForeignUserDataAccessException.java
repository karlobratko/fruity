package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class ForeignUserDataAccessException extends UnauthorizedException {

  public ForeignUserDataAccessException() {
    super(Constants.exceptionMessageFormat);
  }

  public ForeignUserDataAccessException(Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Pokušaj neovlaštenog pristupa podacima drugih korisnika.";

  }

}
