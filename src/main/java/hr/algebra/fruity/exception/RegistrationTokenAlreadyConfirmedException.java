package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class RegistrationTokenAlreadyConfirmedException extends BadRequestException {

  public RegistrationTokenAlreadyConfirmedException() {
    super(Constants.exceptionMessageFormat);
  }

  public RegistrationTokenAlreadyConfirmedException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Registracijski token je već bio potvrđen.";

  }

}