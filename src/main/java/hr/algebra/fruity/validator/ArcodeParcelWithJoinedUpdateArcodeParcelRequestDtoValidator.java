package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateArcodeParcelRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArcodeParcelWithJoinedUpdateArcodeParcelRequestDtoValidator implements WithValidator<ArcodeParcel, JoinedUpdateArcodeParcelRequestDto> {

  private final ArcodeParcelRepository arcodeParcelRepository;

  @Override
  public void validate(ArcodeParcel target, JoinedUpdateArcodeParcelRequestDto with) {
    if (Objects.nonNull(with.name()) || Objects.nonNull(with.cadastralParcel()))
      arcodeParcelRepository.findByNameAndCadastralParcel(
          Objects.requireNonNullElse(with.name(), target.getName()),
          Objects.requireNonNullElse(with.cadastralParcel(), target.getCadastralParcel())
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
