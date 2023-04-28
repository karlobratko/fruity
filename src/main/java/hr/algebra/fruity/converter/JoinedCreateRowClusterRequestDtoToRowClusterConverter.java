package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.joined.JoinedCreateRowClusterRequestDto;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.service.CurrentRequestUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinedCreateRowClusterRequestDtoToRowClusterConverter implements Converter<JoinedCreateRowClusterRequestDto, RowCluster> {

  private final CurrentRequestUserService currentRequestUserService;

  @Override
  public RowCluster convert(@NonNull JoinedCreateRowClusterRequestDto source) {
    return RowCluster.builder()
      .user(currentRequestUserService.getUser())
      .arcodeParcel(source.arcodeParcel())
      .name(source.name())
      .surface(source.surface())
      .build();
  }

}
