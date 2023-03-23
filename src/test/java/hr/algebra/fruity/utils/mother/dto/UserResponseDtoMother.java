package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.UserResponseDto;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDtoMother {

  public static UserResponseDto completeAndBuilt() {
    return new UserResponseDto(
      Constants.instanceId,
      Constants.instanceUuid,
      Constants.instanceName,
      Constants.instanceOib,
      Constants.instancePhoneNumber,
      Constants.instanceAddress,
      Constants.instanceCountryFk
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String instanceOib = "01234567890";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final String instanceAddress = "address";

    public static final Integer instanceCountryFk = 1;

  }

}
