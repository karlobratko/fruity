package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistrationTokenResponseDtoMother {

  public static RegistrationTokenResponseDto.RegistrationTokenResponseDtoBuilder complete() {
    return RegistrationTokenResponseDto.builder()
      .registrationToken(Constants.instanceRegistrationToken)
      .expireDateTime(Constants.instanceExpireDateTime);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final UUID instanceRegistrationToken = UUID.randomUUID();

    public static final LocalDateTime instanceExpireDateTime = LocalDateTime.now().plusMinutes(15);

  }

}
