package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.RegisterRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterRequestDtoMother {

  public static RegisterRequestDto completeAndBuilt() {
    return new RegisterRequestDto(
      Constants.instanceFirstName,
      Constants.instanceLastName,
      Constants.instanceUsername,
      Constants.instanceEmail,
      Constants.instanceOib,
      Constants.instancePassword,
      Constants.instanceConfirmRegistrationUrl
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceFirstName = "firstName";

    public static final String instanceLastName = "lastName";

    public static final String instanceUsername = "username";

    public static final String instanceEmail = "email@email.com";

    public static final String instanceOib = "01234567890";

    public static final String instancePassword = "password";

    public static final String instanceConfirmRegistrationUrl = "http://localhost:3000/confirm-registration";

  }

}
