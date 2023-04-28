package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.UserToFullUserResponseDtoConverter;
import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.mapper.UserMapper;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.UserService;
import hr.algebra.fruity.validator.UserWithUpdateUserRequestDtoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserUserService implements UserService {

  private final UserToFullUserResponseDtoConverter toFullUserResponseDtoConverter;

  private final UserWithUpdateUserRequestDtoValidator userWithUpdateUserRequestDtoValidator;

  private final UserMapper userMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final UserRepository userRepository;

  @Override
  public FullUserResponseDto getCurrentUser() {
    return toFullUserResponseDtoConverter.convert(currentRequestUserService.getUser());
  }

  @Override
  @Transactional
  public FullUserResponseDto updateCurrentUser(UpdateUserRequestDto requestDto) {
    val user = currentRequestUserService.getUser();

    userWithUpdateUserRequestDtoValidator.validate(user, requestDto);

    return toFullUserResponseDtoConverter.convert(
      userRepository.save(
        userMapper.partialUpdate(user, requestDto)
      )
    );
  }

}
