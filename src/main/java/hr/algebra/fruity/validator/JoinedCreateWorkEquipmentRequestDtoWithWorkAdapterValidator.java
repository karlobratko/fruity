package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateWorkEquipmentRequestDtoWithWorkAdapterValidator implements Validator<JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter> {

  private final WorkEquipmentRepository workEquipmentRepository;

  @Override
  public void validate(JoinedCreateWorkEquipmentRequestDtoWithWorkAdapter target) {
    if (workEquipmentRepository.existsByWorkAndEquipment(target.work(), target.dto().equipment()))
      throw new UniquenessViolatedException("Oprema je već korištena u radu.");
  }

}
