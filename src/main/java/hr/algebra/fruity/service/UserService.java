package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;

public interface UserService {

  FullUserResponseDto getCurrentUser();

  FullUserResponseDto updateCurrentUser(UpdateUserRequestDto requestDto);

}
