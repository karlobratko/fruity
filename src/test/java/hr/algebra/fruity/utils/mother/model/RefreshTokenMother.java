package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.RefreshToken;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenMother {

  public static RefreshToken.RefreshTokenBuilder complete() {
    return RefreshToken.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .expireDateTime(Constants.instanceExpireDateTime)
      .employee(Constants.instanceEmployee);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final LocalDateTime instanceExpireDateTime = LocalDateTime.now().plusMinutes(15);

    public static final Employee instanceEmployee = EmployeeMother.complete().build();

  }

}
