package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.ReplaceUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;

public interface UserService {

  FullUserResponseDto getUserById(Long id);

  UserResponseDto updateUserById(Long id, ReplaceUserRequestDto requestDto);

}
