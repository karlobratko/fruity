package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateRealisationEquipmentRequestDto;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.model.RealisationEquipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class RealisationEquipmentWithUpdateRealisationEquipmentRequestDtoValidator implements WithValidator<RealisationEquipment, UpdateRealisationEquipmentRequestDto> {

  @Override
  public void validate(RealisationEquipment target, UpdateRealisationEquipmentRequestDto with) {
    if (target.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();
  }

}
