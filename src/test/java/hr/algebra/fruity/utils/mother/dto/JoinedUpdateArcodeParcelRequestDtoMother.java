package hr.algebra.fruity.utils.mother.dto;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateArcodeParcelRequestDto;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.utils.mother.model.CadastralParcelMother;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinedUpdateArcodeParcelRequestDtoMother {

  public static JoinedUpdateArcodeParcelRequestDto.JoinedUpdateArcodeParcelRequestDtoBuilder complete() {
    return JoinedUpdateArcodeParcelRequestDto.builder()
      .cadastralParcel(Constants.instanceCadastralParcel)
      .name(Constants.instanceName)
      .arcode(Constants.instanceArcode)
      .surface(Constants.instanceSurface);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceName = "name";

    public static final CadastralParcel instanceCadastralParcel = CadastralParcelMother.complete().build();

    public static final BigDecimal instanceSurface = BigDecimal.ZERO;

    public static final Integer instanceArcode = 1;

  }

}
