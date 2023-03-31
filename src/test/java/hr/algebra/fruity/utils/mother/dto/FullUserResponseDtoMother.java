package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullUserResponseDtoMother {

  public static FullUserResponseDto.FullUserResponseDtoBuilder complete() {
    return FullUserResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName)
      .oib(Constants.instanceOib)
      .phoneNumber(Constants.instancePhoneNumber)
      .address(Constants.instanceAddress)
      .county(Constants.instanceCounty);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final String instanceName = "name";

    public static final String instanceOib = "01234567890";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final String instanceAddress = "address";

    public static final CountyResponseDto instanceCounty = CountyResponseDtoMother.complete().build();

  }

}
