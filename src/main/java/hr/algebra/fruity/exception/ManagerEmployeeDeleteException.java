package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class ManagerEmployeeDeleteException extends BadRequestException {

  public ManagerEmployeeDeleteException() {
    super(Constants.exceptionMessageFormat);
  }

  public ManagerEmployeeDeleteException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Brisanje voditelja nije moguće.";

  }

}