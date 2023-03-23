package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.request.RegisterRequestDto;
import hr.algebra.fruity.model.User;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegisterRequestDtoToUserConverter implements Converter<RegisterRequestDto, User> {

  @Override
  public User convert(@NonNull RegisterRequestDto source) {
    return User.builder()
      .oib(source.oib())
      .build();
  }

}
