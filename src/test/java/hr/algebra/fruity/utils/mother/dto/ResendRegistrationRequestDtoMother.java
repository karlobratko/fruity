package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.ResendRegistrationRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResendRegistrationRequestDtoMother {

  public static ResendRegistrationRequestDto.ResendRegistrationRequestDtoBuilder complete() {
    return ResendRegistrationRequestDto.builder()
      .confirmRegistrationUrl(Constants.instanceConfirmRegistrationUrl);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceConfirmRegistrationUrl = "http://localhost:3000/confirm-registration";

  }

}
