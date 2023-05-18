package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class WorkAlreadyFinishedException extends BadRequestException {

  public WorkAlreadyFinishedException() {
    super(Constants.exceptionMessageFormat);
  }

  public WorkAlreadyFinishedException(final Throwable cause) {
    super(Constants.exceptionMessageFormat, cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Dodavanje, uređivanje i brisanje informacija o realizaciji nije moguće jer je rad označen kao dovršen.";

  }

}