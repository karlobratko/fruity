package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUserRequestDtoMother {

  public static UpdateUserRequestDto.UpdateUserRequestDtoBuilder complete() {
    return UpdateUserRequestDto.builder()
      .name(Constants.instanceName)
      .oib(Constants.instanceOib)
      .phoneNumber(Constants.instancePhoneNumber)
      .address(Constants.instanceAddress)
      .countyFk(Constants.instanceCountyFk);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final String instanceOib = "01234567890";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final String instanceAddress = "address";

    public static final Integer instanceCountyFk = 1;

  }

}
