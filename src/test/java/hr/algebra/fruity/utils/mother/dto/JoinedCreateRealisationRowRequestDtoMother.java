package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRowRequestDto;
import hr.algebra.fruity.model.Row;
import java.util.Collections;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedCreateRealisationRowRequestDtoMother {

  public static JoinedCreateRealisationRowRequestDto.JoinedCreateRealisationRowRequestDtoBuilder complete() {
    return JoinedCreateRealisationRowRequestDto.builder()
      .rows(Constants.instanceRows)
      .note(Constants.instanceNote);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final Set<Row> instanceRows = Collections.emptySet();

    public static final String instanceNote = "note";

  }

}
