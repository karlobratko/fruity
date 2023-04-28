package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateEquipmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.EquipmentRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentWithJoinedUpdateEquipmentRequestDtoValidator implements WithValidator<Equipment, JoinedUpdateEquipmentRequestDto> {

  private final EquipmentRepository equipmentRepository;

  @Override
  public void validate(Equipment target, JoinedUpdateEquipmentRequestDto with) {
    if (Objects.nonNull(with.name()))
      equipmentRepository.findByNameAndUser(with.name(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
        });
  }

}
