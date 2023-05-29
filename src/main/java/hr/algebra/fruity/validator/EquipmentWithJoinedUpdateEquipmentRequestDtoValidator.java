package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateEquipmentRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.EquipmentRepository;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentWithJoinedUpdateEquipmentRequestDtoValidator implements WithValidator<Equipment, JoinedUpdateEquipmentRequestDto> {

  private final EquipmentRepository equipmentRepository;

  private final EntityManager entityManager;

  @Override
  public void validate(Equipment target, JoinedUpdateEquipmentRequestDto with) {
    val session = entityManager.unwrap(Session.class);
    val filter = session.enableFilter("isNotDeleted");

    if (Objects.nonNull(with.name()))
      equipmentRepository.findByNameAndUser(with.name(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
        });

    session.disableFilter("isNotDeleted");
  }

}
