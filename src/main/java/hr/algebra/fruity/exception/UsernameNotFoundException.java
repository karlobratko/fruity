package hr.algebra.fruity.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class UsernameNotFoundException extends NotFoundException {

  public UsernameNotFoundException(String username) {
    super(Constants.exceptionMessageFormat.formatted(username));
  }

  public UsernameNotFoundException(String username, Throwable cause) {
    super(Constants.exceptionMessageFormat.formatted(username), cause);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String exceptionMessageFormat = "Korisničko ime %s ne postoji.";

  }

}
