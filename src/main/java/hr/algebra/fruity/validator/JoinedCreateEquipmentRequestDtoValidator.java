package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateEquipmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateEquipmentRequestDtoValidator implements Validator<JoinedCreateEquipmentRequestDto> {

  private final EquipmentRepository equipmentRepository;

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public void validate(JoinedCreateEquipmentRequestDto target) {
    equipmentRepository.findByNameAndUserId(target.name(), currentRequestUserService.getUserId())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
      });
  }

}