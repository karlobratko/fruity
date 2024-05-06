package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.UnitOfMeasure;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitOfMeasureMother {

  public static UnitOfMeasure.UnitOfMeasureBuilder complete() {
    return UnitOfMeasure.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
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

    public static final UnitOfMeasure instanceBase = null;

    public static final BigDecimal instanceBaseMultiplier = BigDecimal.ONE;

  }

}