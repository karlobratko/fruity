package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.ConfirmRegistrationRequestDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmRegistrationRequestDtoMother {

  public static ConfirmRegistrationRequestDto.ConfirmRegistrationRequestDtoBuilder complete() {
    return ConfirmRegistrationRequestDto.builder()
      .registrationToken(Constants.instanceRegistrationToken);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final UUID instanceRegistrationToken = UUID.randomUUID();

  }

}