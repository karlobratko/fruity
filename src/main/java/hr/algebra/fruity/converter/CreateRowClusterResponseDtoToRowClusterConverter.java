package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.CreateRowClusterRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRowClusterResponseDtoToRowClusterConverter implements Converter<CreateRowClusterRequestDto, RowCluster> {

  private final CurrentRequestUserService currentRequestUserService;

  private final ArcodeParcelRepository arcodeParcelRepository;

  @Override
  public RowCluster convert(@NonNull CreateRowClusterRequestDto source) {
    return RowCluster.builder()
      .user(currentRequestUserService.getUser())
      .arcodeParcel(arcodeParcelRepository.findById(source.arcodeParcelFk()).orElseThrow(EntityNotFoundException::new))
      .name(source.name())
      .surface(source.surface())
      .build();
  }

}
