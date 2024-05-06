package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CadastralParcelOwnershipStatusResponseDtoMother {

  public static CadastralParcelOwnershipStatusResponseDto.CadastralParcelOwnershipStatusResponseDtoBuilder complete() {
    return CadastralParcelOwnershipStatusResponseDto.builder()
      .id(Constants.instanceId)
      .name(Constants.instanceName);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceId = 1;

    public static final String instanceName = "name";

    public static final String instanceDisplayName = "displayName";

  }

}