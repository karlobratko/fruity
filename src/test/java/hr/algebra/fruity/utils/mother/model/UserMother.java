package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.County;
import hr.algebra.fruity.model.User;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMother {

  public static User.UserBuilder complete() {
    return User.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .name(Constants.instanceName)
      .oib(Constants.instanceOib)
      .phoneNumber(Constants.instancePhoneNumber)
      .address(Constants.instanceAddress)
      .county(Constants.instanceCountry);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final String instanceName = "name";

    public static final String instanceOib = "01234567890";

    public static final String instancePhoneNumber = "123/123-1234";

    public static final String instanceAddress = "address";

    public static final County instanceCountry = CountyMother.complete().build();

  }

}
