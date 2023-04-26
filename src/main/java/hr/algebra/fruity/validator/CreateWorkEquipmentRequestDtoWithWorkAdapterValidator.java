package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateWorkEquipmentRequestDtoWithWorkAdapter;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.WorkEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkEquipmentRequestDtoWithWorkAdapterValidator implements Validator<CreateWorkEquipmentRequestDtoWithWorkAdapter> {

  private final WorkEquipmentRepository workEquipmentRepository;

  @Override
  public void validate(CreateWorkEquipmentRequestDtoWithWorkAdapter target) {
    if (workEquipmentRepository.existsByWorkIdAndEquipmentId(target.work().getId(), target.dto().equipmentFk()))
      throw new UniquenessViolatedException("Oprema je već korištena u radu.");
  }

}
