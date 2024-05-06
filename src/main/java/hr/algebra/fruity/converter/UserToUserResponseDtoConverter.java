package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.UserResponseDto;
import hr.algebra.fruity.model.User;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseDtoConverter implements Converter<User, UserResponseDto> {

  @Override
  public UserResponseDto convert(@NonNull User source) {
    return new UserResponseDto(
      source.getId(),
      source.getName(),
      source.getOib(),
      source.getPhoneNumber(),
      source.getAddress(),
      Objects.nonNull(source.getCounty()) ? source.getCounty().getId() : null
    );
  }

}
