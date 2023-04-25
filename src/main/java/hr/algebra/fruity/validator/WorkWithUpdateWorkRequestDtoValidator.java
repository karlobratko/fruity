package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.model.Work;
import org.springframework.stereotype.Component;

@Component
public class WorkWithUpdateWorkRequestDtoValidator implements WithValidator<Work, UpdateWorkRequestDto> {

  @Override
  public void validate(Work target, UpdateWorkRequestDto with) {

  }

}
