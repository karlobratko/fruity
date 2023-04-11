package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastralParcelWithUpdateCadastralParcelRequestDtoValidator implements WithValidator<CadastralParcel, UpdateCadastralParcelRequestDto> {

  private final CadastralParcelRepository cadastralParcelRepository;

  @Override
  public void validate(CadastralParcel target, UpdateCadastralParcelRequestDto with) {
    if (Objects.nonNull(with.name()))
      cadastralParcelRepository.findByNameAndUser(with.name(), target.getUser())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji i nije jedinstven.");
        });

    if (Objects.nonNull(with.cadastralMunicipalityFk()) || Objects.nonNull(with.cadastralNumber()))
      cadastralParcelRepository.findByCadastralMunicipalityIdAndCadastralNumber(
          Objects.requireNonNullElse(with.cadastralMunicipalityFk(), target.getCadastralMunicipality().getId()),
          Objects.requireNonNullElse(with.cadastralNumber(), target.getCadastralNumber())
        )
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Katastarski broj već postoji unutar katastarske općine i nije jedinstven.");
        });
  }

}
