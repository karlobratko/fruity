package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.model.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CadastralParcelMother {

  public static CadastralParcel.CadastralParcelBuilder complete() {
    return CadastralParcel.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .user(Constants.instanceUser)
      .name(Constants.instanceName)
      .ownershipStatus(Constants.instanceOwnershipStatus)
      .cadastralMunicipality(Constants.instanceCadastralMunicipality)
      .surface(Constants.instanceSurface)
      .cadastralNumber(Constants.instanceCadastralNumber);
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

    public static final CadastralParcelOwnershipStatus instanceOwnershipStatus = CadastralParcelOwnershipStatusMother.complete().build();

    public static final CadastralMunicipality instanceCadastralMunicipality = CadastralMunicipalityMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final String instanceCadastralNumber = "cadastralNumber";

  }

}
