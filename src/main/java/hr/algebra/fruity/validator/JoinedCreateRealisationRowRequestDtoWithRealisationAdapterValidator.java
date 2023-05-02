package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRowRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.NoNewRowsForWorkRowCreationException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import org.springframework.stereotype.Component;

@Component
public class JoinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator implements Validator<JoinedCreateRealisationRowRequestDtoWithRealisationAdapter> {

  @Override
  public void validate(JoinedCreateRealisationRowRequestDtoWithRealisationAdapter target) {
    if (target.realisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();


    if (target.dto().rows().isEmpty())
      throw new NoNewRowsForWorkRowCreationException();
  }

}
