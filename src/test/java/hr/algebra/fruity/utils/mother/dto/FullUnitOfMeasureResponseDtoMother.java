package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.response.FullUnitOfMeasureResponseDto;
import hr.algebra.fruity.dto.response.UnitOfMeasureResponseDto;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FullUnitOfMeasureResponseDtoMother {

  public static FullUnitOfMeasureResponseDto.FullUnitOfMeasureResponseDtoBuilder complete() {
    return FullUnitOfMeasureResponseDto.builder()
      .id(Constants.instanceId)
      .abbreviation(Constants.instanceAbbreviation)
      .name(Constants.instanceName)
      .base(Constants.instanceBase)
      .baseMultiplier(Constants.instanceBaseMultiplier);
  }

  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final String instanceName = "name";

    public static final String instanceAbbreviation = "abbreviation";

    public static final UnitOfMeasureResponseDto instanceBase = null;

    public static final BigDecimal instanceBaseMultiplier = BigDecimal.ONE;

  }

}