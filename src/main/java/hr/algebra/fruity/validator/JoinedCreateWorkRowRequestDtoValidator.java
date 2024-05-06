package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkRowRequestDto;
import hr.algebra.fruity.exception.NoNewRowsForWorkRowCreationException;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateWorkRowRequestDtoValidator implements Validator<JoinedCreateWorkRowRequestDto> {

  @Override
  public void validate(JoinedCreateWorkRowRequestDto target) {
    if (target.rows().isEmpty())
      throw new NoNewRowsForWorkRowCreationException();
  }

}
