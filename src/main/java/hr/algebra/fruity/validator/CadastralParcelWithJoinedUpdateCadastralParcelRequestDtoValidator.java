package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateCadastralParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastralParcelWithJoinedUpdateCadastralParcelRequestDtoValidator implements WithValidator<CadastralParcel, JoinedUpdateCadastralParcelRequestDto> {

  private final CadastralParcelRepository cadastralParcelRepository;

  @Override
  public void validate(CadastralParcel target, JoinedUpdateCadastralParcelRequestDto with) {
    if (Objects.nonNull(with.name()))
      cadastralParcelRepository.findByNameAndUser(with.name(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
        });

    if (Objects.nonNull(with.cadastralMunicipality()) || Objects.nonNull(with.cadastralNumber()))
      cadastralParcelRepository.findByCadastralMunicipalityAndCadastralNumber(
          Objects.requireNonNullElse(with.cadastralMunicipality(), target.getCadastralMunicipality()),
          Objects.requireNonNullElse(with.cadastralNumber(), target.getCadastralNumber())
        )
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Katastarski broj već postoji unutar katastarske općine i nije jedinstven.");
        });
  }

}
