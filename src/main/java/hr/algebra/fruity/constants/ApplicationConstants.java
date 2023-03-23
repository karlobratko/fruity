package hr.algebra.fruity.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConstants {

  public static final String apiRequestMapping = "/api/v1";

  public static final String apiUserManagementRequestMapping = apiRequestMapping + "/user-management";

  public static final String apiInventoryManagementRequestMapping = apiRequestMapping + "/inventory-management";

  public static final String apiResourceManagementRequestMapping = apiRequestMapping + "/resource-management";

  public static final String apiWorkManagementRequestMapping = apiRequestMapping + "/work-management";

}
