package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.ReplaceUserRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplaceUserRequestDtoMother {

  public static ReplaceUserRequestDto completeAndBuilt() {
    return new ReplaceUserRequestDto(
      Constants.instanceName,
      Constants.instanceOib,
      Constants.instancePhoneNumber,
      Constants.instanceAddress,
      Constants.instanceCountryFk
    );
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final String instanceOib = "01234567890";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final String instanceAddress = "address";

    public static final Integer instanceCountryFk = 1;

  }

}
