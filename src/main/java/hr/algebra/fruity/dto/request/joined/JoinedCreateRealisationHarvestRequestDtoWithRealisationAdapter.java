package hr.algebra.fruity.dto.request.joined;

import hr.algebra.fruity.model.Realisation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter(
  JoinedCreateRealisationHarvestRequestDto dto,
  Realisation realisation
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String dto = "dto";

    public static final String realisation = "realisation";

  }

}
