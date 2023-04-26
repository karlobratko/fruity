package hr.algebra.fruity.dto.request;

import hr.algebra.fruity.model.Work;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record CreateWorkRowRequestDtoWithWorkAdapter(
  CreateWorkRowRequestDto dto,
  Work work
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String dto = "dto";

    public static final String work = "work";

  }

}
