package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.mapper.UserMapper;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.UserService;
import hr.algebra.fruity.validator.UserWithUpdateUserRequestDtoValidator;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final ConversionService conversionService;

  private final UserWithUpdateUserRequestDtoValidator userWithUpdateUserRequestDtoValidator;

  private final UserMapper userMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final UserRepository userRepository;

  @Override
  public FullUserResponseDto getUserById(Long id) {
    return conversionService.convert(getUser(id), FullUserResponseDto.class);
  }

  @Override
  public FullUserResponseDto getCurrentUser() {
    return conversionService.convert(currentRequestUserService.getUser(), FullUserResponseDto.class);
  }

  @Override
  public UserResponseDto updateUserById(Long id, UpdateUserRequestDto requestDto) {
    val user = getUser(id);

    userWithUpdateUserRequestDtoValidator.validate(user, requestDto);

    return conversionService.convert(
      userRepository.save(
        userMapper.partialUpdate(user, requestDto)
      ),
      UserResponseDto.class
    );
  }

  @Override
  public UserResponseDto updateCurrentUser(UpdateUserRequestDto requestDto) {
    val user = currentRequestUserService.getUser();

    userWithUpdateUserRequestDtoValidator.validate(user, requestDto);

    return conversionService.convert(
      userRepository.save(
        userMapper.partialUpdate(user, requestDto)
      ),
      UserResponseDto.class
    );
  }

  private User getUser(Long id) {
    val user = userRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    if (!Objects.equals(user, currentRequestUserService.getUser()))
      throw new ForeignUserDataAccessException();

    return user;
  }

}
