package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.LoginMobileRequestDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMobileRequestDtoMother {

  public static LoginMobileRequestDto.LoginMobileRequestDtoBuilder complete() {
    return LoginMobileRequestDto.builder()
      .mobileToken(Constants.instanceMobileToken);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final UUID instanceMobileToken = UUID.randomUUID();

  }

}