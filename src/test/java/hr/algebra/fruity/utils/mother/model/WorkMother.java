package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.User;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkMother {

  public static Work.WorkBuilder complete() {
    return Work.builder()
      .id(Constants.instanceId)
      .uuid(Constants.instanceUuid)
      .createDate(Constants.instanceCreateDate)
      .updateDate(Constants.instanceUpdateDate)
      .deleteDate(Constants.instanceDeleteDate)
      .user(Constants.instanceUser)
      .startDateTime(Constants.instanceStartDateTime)
      .endDateTime(Constants.instanceEndDateTime)
      .finished(Constants.instanceFinished)
      .note(Constants.instanceNote)
      .type(Constants.instanceWorkType);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Long instanceId = 1L;

    public static final UUID instanceUuid = UUID.randomUUID();

    public static final LocalDate instanceCreateDate = LocalDate.now();

    public static final LocalDate instanceUpdateDate = LocalDate.now();

    public static final LocalDate instanceDeleteDate = null;

    public static final User instanceUser = UserMother.complete().build();

    public static final LocalDateTime instanceStartDateTime = LocalDateTime.now();

    public static final LocalDateTime instanceEndDateTime = LocalDateTime.now();

    public static final boolean instanceFinished = false;

    public static final String instanceNote = "note";

    public static final WorkType instanceWorkType = WorkTypeMother.complete().build();

  }

}
