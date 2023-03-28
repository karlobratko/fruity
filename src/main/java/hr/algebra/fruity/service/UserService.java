package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;

public interface UserService {

  FullUserResponseDto getUserById(Long id);

  UserResponseDto updateUserById(Long id, UpdateUserRequestDto requestDto);

}
