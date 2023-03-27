package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class EntityNotFoundException extends NotFoundException {

  public EntityNotFoundException() {
    super(Constants.exceptionMessageFormat);
  }

  public EntityNotFoundException(Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Entitet s danim identifikatorom ne postoji.";

  }

}
