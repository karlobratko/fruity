package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateRealisationHarvestRequestDto;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.model.RealisationHarvest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class RealisationHarvestWithUpdateRealisationHarvestRequestDtoValidator implements WithValidator<RealisationHarvest, UpdateRealisationHarvestRequestDto> {

  @Override
  public void validate(RealisationHarvest target, UpdateRealisationHarvestRequestDto with) {
    if (target.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();
  }

}
