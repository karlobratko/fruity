package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.CreateEquipmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEquipmentRequestDtoValidator implements Validator<CreateEquipmentRequestDto> {

  private final EquipmentRepository equipmentRepository;

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public void validate(CreateEquipmentRequestDto target) {
    equipmentRepository.findByNameAndUser(target.name(), currentRequestUserService.getUser())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
      });
  }

}