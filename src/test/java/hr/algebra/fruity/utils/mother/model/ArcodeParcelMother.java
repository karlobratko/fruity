package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArcodeParcelMother {

  public static ArcodeParcel.ArcodeParcelBuilder complete() {
    return ArcodeParcel.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .user(Constants.instanceUser)
      .cadastralParcel(Constants.instanceCadastralParcel)
      .name(Constants.instanceName)
      .arcode(Constants.instanceArcode)
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

    public static final CadastralParcel instanceCadastralParcel = CadastralParcelMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final Integer instanceArcode = 1;

  }

}
