package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArcodeParcelWithUpdateArcodeParcelRequestDtoValidator implements WithValidator<ArcodeParcel, UpdateArcodeParcelRequestDto> {

  private final ArcodeParcelRepository arcodeParcelRepository;

  @Override
  public void validate(ArcodeParcel target, UpdateArcodeParcelRequestDto with) {
    if (Objects.nonNull(with.name()) || Objects.nonNull(with.cadastralParcelFk()))
      arcodeParcelRepository.findByNameAndCadastralParcelId(
          Objects.requireNonNullElse(with.name(), target.getName()),
          Objects.requireNonNullElse(with.cadastralParcelFk(), target.getCadastralParcel().getId())
        )
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("Naziv već postoji unutar katastarske čestice i nije jedinstven.");
        });

    if (Objects.nonNull(with.arcode()))
      arcodeParcelRepository.findByArcode(with.arcode())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("ARKOD već postoji i nije jedinstven.");
        });
  }

}
