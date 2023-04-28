package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class NonManagerUpdateRealisationEmployeeException extends BadRequestException {

  public NonManagerUpdateRealisationEmployeeException() {
    super(Constants.exceptionMessageFormat);
  }

  public NonManagerUpdateRealisationEmployeeException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Samo voditelji mogu promijeniti realizatora rada.";

  }

}