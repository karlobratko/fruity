package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.mapper.UserMapper;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.UserService;
import hr.algebra.fruity.validator.UserWithUpdateUserRequestDtoWithValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final ConversionService conversionService;

  private final UserWithUpdateUserRequestDtoWithValidator userWithUpdateUserRequestDtoValidator;

  private final UserMapper userMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final UserRepository userRepository;

  @Override
  public FullUserResponseDto getCurrentUser() {
    return conversionService.convert(currentRequestUserService.getUser(), FullUserResponseDto.class);
  }

  @Override
  @Transactional
  public FullUserResponseDto updateCurrentUser(UpdateUserRequestDto requestDto) {
    val user = currentRequestUserService.getUser();

    userWithUpdateUserRequestDtoValidator.validate(user, requestDto);

    return conversionService.convert(
      userRepository.save(
        userMapper.partialUpdate(user, requestDto)
      ),
      FullUserResponseDto.class
    );
  }

}
