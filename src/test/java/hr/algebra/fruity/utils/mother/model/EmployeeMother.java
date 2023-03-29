package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.EmployeeRole;
import hr.algebra.fruity.model.RefreshToken;
import hr.algebra.fruity.model.RegistrationToken;
import hr.algebra.fruity.model.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMother {

  public static Employee.EmployeeBuilder complete() {
    return Employee.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .user(Constants.instanceUser)
      .firstName(Constants.instanceFirstName)
      .lastName(Constants.instanceLastName)
      .username(Constants.instanceUsername)
      .email(Constants.instanceEmail)
      .phoneNumber(Constants.instancePhoneNumber)
      .costPerHour(Constants.instanceCostPerHour)
      .password(Constants.instancePassword)
      .enabled(Constants.instanceEnabled)
      .locked(Constants.instanceLocked)
      .role(Constants.instanceEmployeeRole)
      .registrationToken(Constants.instanceRegistrationToken)
      .refreshToken(Constants.instanceRefreshToken);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final User instanceUser = UserMother.complete().build();

    public static final String instanceFirstName = "firstName";

    public static final String instanceLastName = "lastName";

    public static final String instanceUsername = "username";

    public static final String instanceEmail = "employee@email.com";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final BigDecimal instanceCostPerHour = BigDecimal.ZERO;

    public static final String instancePassword = "password";

    public static final boolean instanceEnabled = true;

    public static final boolean instanceLocked = false;

    public static final EmployeeRole instanceEmployeeRole = EmployeeRoleMother.complete().build();

    public static final RegistrationToken instanceRegistrationToken = RegistrationTokenMother.complete().build();

    public static final RefreshToken instanceRefreshToken = RefreshTokenMother.complete().build();

  }

}
