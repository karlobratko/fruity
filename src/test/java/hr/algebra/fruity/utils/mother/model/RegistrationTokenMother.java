package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.RegistrationToken;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistrationTokenMother {

  public static RegistrationToken.RegistrationTokenBuilder complete() {
    return RegistrationToken.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .createDateTime(Constants.instanceCreateDateTime)
      .confirmDateTime(Constants.instanceConfirmDateTime)
      .expireDateTime(Constants.instanceExpireDateTime);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final LocalDateTime instanceCreateDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceConfirmDateTime = null;

    public static final LocalDateTime instanceExpireDateTime = LocalDateTime.now().plusMinutes(15);

  }

}
