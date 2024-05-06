package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class NoNewRowsForWorkRowCreationException extends BadRequestException {

  public NoNewRowsForWorkRowCreationException() {
    super(Constants.exceptionMessageFormat);
  }

  public NoNewRowsForWorkRowCreationException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Barem jedan novi red mora biti odabran kako bi se dodao u rad.";

  }

}