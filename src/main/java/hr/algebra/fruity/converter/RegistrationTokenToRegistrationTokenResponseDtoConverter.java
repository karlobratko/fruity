package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.RegistrationTokenResponseDto;
import hr.algebra.fruity.model.RegistrationToken;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegistrationTokenToRegistrationTokenResponseDtoConverter implements Converter<RegistrationToken, RegistrationTokenResponseDto> {

  @Override
  public RegistrationTokenResponseDto convert(@NonNull RegistrationToken source) {
    return new RegistrationTokenResponseDto(
      source.getId(),
      source.getUuid(),
      source.getCreateDateTime(),
      source.getExpireDateTime(),
      source.getConfirmDateTime()
    );
  }

}
