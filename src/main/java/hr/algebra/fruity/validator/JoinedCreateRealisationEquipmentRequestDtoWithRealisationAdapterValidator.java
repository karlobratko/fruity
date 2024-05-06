package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.repository.RealisationEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapterValidator implements Validator<JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter> {

  private final RealisationEquipmentRepository realisationEquipmentRepository;

  @Override
  public void validate(JoinedCreateRealisationEquipmentRequestDtoWithRealisationAdapter target) {
    if (target.realisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    if (realisationEquipmentRepository.existsByRealisationAndEquipment(target.realisation(), target.dto().equipment()))
      throw new UniquenessViolatedException("Oprema je već korištena u realizaciji.");
  }

}
