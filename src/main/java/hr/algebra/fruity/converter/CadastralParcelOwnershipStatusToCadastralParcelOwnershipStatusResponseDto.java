package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.CadastralParcelOwnershipStatusResponseDto;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CadastralParcelOwnershipStatusToCadastralParcelOwnershipStatusResponseDto implements Converter<CadastralParcelOwnershipStatus, CadastralParcelOwnershipStatusResponseDto> {

  @Override
  public CadastralParcelOwnershipStatusResponseDto convert(@NonNull CadastralParcelOwnershipStatus source) {
    return new CadastralParcelOwnershipStatusResponseDto(
      source.getId(),
      source.getDisplayName()
    );
  }

}
