package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.CadastralMunicipality;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CadastralMunicipalityMother {

  public static CadastralMunicipality.CadastralMunicipalityBuilder complete() {
    return CadastralMunicipality.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .registrationNumber(Constants.instanceRegistrationNumber)
      .name(Constants.instanceName)
      .department(Constants.instanceDepartment)
      .regionalCadastreOffice(Constants.instanceRegionalCadastreOffice);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Integer instanceId = 1;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final Integer instanceRegistrationNumber = 1;

    public static final String instanceName = "name";

    public static final String instanceDepartment = "department";

    public static final String instanceRegionalCadastreOffice = "regionalCadastreOffice";

  }

}
