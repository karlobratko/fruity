package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.LoginRequestDto;
import hr.algebra.fruity.dto.request.RegisterRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDtoMother {

  public static LoginRequestDto.LoginRequestDtoBuilder complete() {
    return LoginRequestDto.builder()
      .username(Constants.instanceUsername)
      .password(Constants.instancePassword);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceUsername = "username";

    public static final String instancePassword = "password";

  }

}
