package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.model.User;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToFullUserResponseDtoConverter implements Converter<User, FullUserResponseDto> {

  private final CountyToCountyResponseDtoConverter countyConverter;

  @Override
  public FullUserResponseDto convert(@NonNull User source) {
    return new FullUserResponseDto(
      source.getId(),
      source.getName(),
      source.getOib(),
      source.getPhoneNumber(),
      source.getAddress(),
      Objects.nonNull(source.getCounty()) ? countyConverter.convert(source.getCounty()) : null
    );
  }

}
