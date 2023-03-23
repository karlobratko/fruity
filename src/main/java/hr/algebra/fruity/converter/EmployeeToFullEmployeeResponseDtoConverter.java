package hr.algebra.fruity.converter;

import hr.algebra.fruity.dto.response.FullEmployeeResponseDto;
import hr.algebra.fruity.model.Employee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeToFullEmployeeResponseDtoConverter implements Converter<Employee, FullEmployeeResponseDto> {

  private final UserToUserResponseDtoConverter userToUserResponseDtoConverter;

  private final EmployeeRoleToEmployeeRoleResponseDtoConverter employeeRoleToEmployeeRoleResponseDtoConverter;

  private final RegistrationTokenToRegistrationTokenResponseDtoConverter registrationTokenToRegistrationTokenResponseDtoConverter;

  @Override
  public FullEmployeeResponseDto convert(@NonNull Employee source) {
    return new FullEmployeeResponseDto(
      source.getId(),
      source.getUuid(),
      userToUserResponseDtoConverter.convert(source.getUser()),
      source.getFirstName(),
      source.getLastName(),
      source.getUsername(),
      source.getEmail(),
      source.getPhoneNumber(),
      source.getCostPerHour(),
      employeeRoleToEmployeeRoleResponseDtoConverter.convert(source.getRole()),
      registrationTokenToRegistrationTokenResponseDtoConverter.convert(source.getRegistrationToken())
    );
  }

}
