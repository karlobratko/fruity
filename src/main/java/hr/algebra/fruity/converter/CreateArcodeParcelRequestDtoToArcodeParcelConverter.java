package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateArcodeParcelRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.repository.CadastralParcelRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateArcodeParcelRequestDtoToArcodeParcelConverter implements Converter<CreateArcodeParcelRequestDto, ArcodeParcel> {

  private final CurrentRequestUserService currentRequestUserService;

  private final CadastralParcelRepository cadastralParcelRepository;

  @Override
  public ArcodeParcel convert(@NonNull CreateArcodeParcelRequestDto source) {
    return ArcodeParcel.builder()
      .user(currentRequestUserService.getUser())
      .name(source.name())
      .arcode(source.arcode())
      .cadastralParcel(cadastralParcelRepository.findById(source.cadastralParcelFk()).orElseThrow(EntityNotFoundException::new))
      .surface(source.surface())
      .build();
  }

}
