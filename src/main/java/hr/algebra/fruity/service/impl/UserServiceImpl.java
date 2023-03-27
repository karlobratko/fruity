package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.ReplaceUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.mapper.UserMapper;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.CurrentUserService;
import hr.algebra.fruity.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final ConversionService conversionService;

  private final UserMapper userMapper;

  private final CurrentUserService currentUserService;

  private final UserRepository userRepository;

  @Override
  public FullUserResponseDto getUserById(Long id) {
    return conversionService.convert(getUser(id), FullUserResponseDto.class);
  }

  @Override
  public UserResponseDto updateUserById(Long id, ReplaceUserRequestDto requestDto) {
    val user = getUser(id);

    validateUserOibUniqueness(user, requestDto.oib());

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

    if (!Objects.equals(user, currentUserService.getLoggedInUser()))
      throw new ForeignUserDataAccessException();

    return user;
  }

  private void validateUserOibUniqueness(User user, String oib) {
    userRepository.findByOib(oib)
      .ifPresent(it -> {
        if (!Objects.equals(it, user))
          throw new UniquenessViolatedException("OIB već postoji i nije jedinstven.");
      });
  }

}
