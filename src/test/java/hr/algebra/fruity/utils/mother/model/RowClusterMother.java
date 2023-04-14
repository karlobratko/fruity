package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.model.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowClusterMother {

  public static RowCluster.RowClusterBuilder complete() {
    return RowCluster.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .user(Constants.instanceUser)
      .arcodeParcel(Constants.instanceArcodeParcel)
      .name(Constants.instanceName)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final User instanceUser = UserMother.complete().build();

    public static final String instanceName = "name";

    public static final ArcodeParcel instanceArcodeParcel = ArcodeParcelMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

  }

}
