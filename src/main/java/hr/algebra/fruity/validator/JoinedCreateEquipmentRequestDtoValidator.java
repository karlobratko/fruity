package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedCreateEquipmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.repository.EquipmentRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateEquipmentRequestDtoValidator implements Validator<JoinedCreateEquipmentRequestDto> {

  private final EquipmentRepository equipmentRepository;

  private final CurrentRequestUserService currentRequestUserService;

  private final EntityManager entityManager;

  @Override
  public void validate(JoinedCreateEquipmentRequestDto target) {
    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    equipmentRepository.findByNameAndUserId(target.name(), currentRequestUserService.getUserId())
      .ifPresent(ignored -> {
        throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
      });

    session.disableFilter("isNotDeleted");
  }

}