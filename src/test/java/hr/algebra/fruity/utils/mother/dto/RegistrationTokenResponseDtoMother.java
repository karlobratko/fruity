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
      .id(Constants.instanceId)
      .token(Constants.instanceToken)
      .createDateTime(Constants.instanceCreateDateTime)
      .confirmDateTime(Constants.instanceConfirmDateTime)
      .expireDateTime(Constants.instanceExpireDateTime);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceToken = UUID.randomUUID();

    public static final LocalDateTime instanceCreateDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceConfirmDateTime = null;

    public static final LocalDateTime instanceExpireDateTime = LocalDateTime.now().plusMinutes(15);

  }

}
