package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.FullUserResponseDto;
import hr.algebra.fruity.dto.response.UserResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.ForeignUserDataAccessException;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.mapper.UserMapper;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.impl.UserServiceImpl;
import hr.algebra.fruity.utils.mother.dto.FullUserResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.ReplaceUserRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.UserResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.UserMother;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Test")
@SuppressWarnings("static-access")
public class UserServiceUnitTest {

  @InjectMocks
  UserServiceImpl userService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private UserMapper userMapper;

  @Mock
  private CurrentUserService currentUserService;

  @Mock
  private UserRepository userRepository;

  @Nested
  @DisplayName("WHEN getUserById is called")
  public class WHEN_getUserById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(userRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getUserById is called
      when(() -> userService.getUserById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... id
      val id = 1L;
      val user = UserMother.complete().build();
      given(userRepository.findById(same(id))).willReturn(Optional.of(user));
      // ... CurrentUserService's logged-in User is not equal to User
      val loggedInUser = UserMother.complete().id(user.getId() + 1).build();
      given(currentUserService.getLoggedInUser()).willReturn(loggedInUser);

      // WHEN
      // ... getUserById is called
      when(() -> userService.getUserById(id));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN FullUserResponseDto is returned")
    public void GIVEN_id_THEN_FullUserResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val user = UserMother.complete().build();
      given(userRepository.findById(same(id))).willReturn(Optional.of(user));
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = user;
      given(currentUserService.getLoggedInUser()).willReturn(user);
      // ... ConversionService successfully converts from User to FullUserResponseDto
      val expectedResponseDto = FullUserResponseDtoMother.complete().build();
      given(conversionService.convert(same(user), same(FullUserResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getUserById is called
      val responseDto = userService.getUserById(id);

      // THEN
      // ... FullUserResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN updateUserById is called")
  public class WHEN_updateUserById {

    @Test
    @DisplayName("GIVEN invalid id and ReplaceUserRequestDto " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIdAndReplaceUserRequestDto_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1L;
      given(userRepository.findById(same(id))).willReturn(Optional.empty());
      // ... ReplaceUserRequestDto
      val requestDto = ReplaceUserRequestDtoMother.complete().build();

      // WHEN
      // ... updateUserById is called
      when(() -> userService.updateUserById(id, requestDto));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id, ReplaceUserRequestDto, and foreign logged-in User " +
      "... THEN ForeignUserDataAccessException is thrown")
    public void GIVEN_idAndReplaceUserRequestDtoAndForeignUser_THEN_ForeignUserDataAccessException() {
      // GIVEN
      // ... id
      val id = 1L;
      val user = UserMother.complete().build();
      given(userRepository.findById(same(id))).willReturn(Optional.of(user));
      // ... ReplaceUserRequestDto
      val requestDto = ReplaceUserRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is not equal to User
      val loggedInUser = UserMother.complete().id(user.getId() + 1).build();
      given(currentUserService.getLoggedInUser()).willReturn(loggedInUser);

      // WHEN
      // ... updateUserById is called
      when(() -> userService.updateUserById(id, requestDto));

      // THEN
      // ... ForeignUserDataAccessException is thrown
      and.then(caughtException())
        .isInstanceOf(ForeignUserDataAccessException.class)
        .hasMessage(ForeignUserDataAccessException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id, ReplaceUserRequestDto, and oib in other User " +
      "... THEN UniquenessViolatedException is thrown")
    public void GIVEN_idAndReplaceUserRequestDtoAndOibInOtherUser_THEN_UniquenessViolatedException() {
      // GIVEN
      // ... id
      val id = 1L;
      val user = UserMother.complete().build();
      given(userRepository.findById(same(id))).willReturn(Optional.of(user));
      // ... ReplaceUserRequestDto
      val requestDto = ReplaceUserRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = user;
      given(currentUserService.getLoggedInUser()).willReturn(loggedInUser);
      // ... oib in other User
      val otherUser = UserMother.complete().id(user.getId() + 1).build();
      given(userRepository.findByOib(same(user.getOib()))).willReturn(Optional.of(otherUser));

      // WHEN
      // ... updateUserById is called
      when(() -> userService.updateUserById(id, requestDto));

      // THEN
      // ... UniquenessViolatedException is thrown
      and.then(caughtException())
        .isInstanceOf(UniquenessViolatedException.class)
        .hasMessage("OIB već postoji i nije jedinstven.")
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id, ReplaceUserRequestDto, and unique oib " +
      "... THEN UserResponseDto is returned")
    public void GIVEN_idAndReplaceUserRequestDtoAndUniqueOib_THEN_UserResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val user = UserMother.complete().build();
      given(userRepository.findById(same(id))).willReturn(Optional.of(user));
      // ... ReplaceUserRequestDto
      val requestDto = ReplaceUserRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = user;
      given(currentUserService.getLoggedInUser()).willReturn(loggedInUser);
      // ... UserRepository fails to find User by oib
      given(userRepository.findByOib(same(user.getOib()))).willReturn(Optional.empty());
      // ... UserMapper successfully partially updates User with ReplaceUserRequestDto
      given(userMapper.partialUpdate(same(user), same(requestDto))).willReturn(user);
      // ... UserRepository successfully saves User
      given(userRepository.save(same(user))).willReturn(user);
      // ... ConversionService successfully converts from User to UserResponseDto
      val expectedResponseDto = UserResponseDtoMother.complete().build();
      given(conversionService.convert(same(user), same(UserResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateUserById is called
      val responseDto = userService.updateUserById(id, requestDto);

      // THEN
      // ... UserResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

    @Test
    @DisplayName("GIVEN id, ReplaceUserRequestDto, and oib in same User " +
      "... THEN UserResponseDto is returned")
    public void GIVEN_idAndReplaceUserRequestDtoAndOibInSameUser_THEN_UserResponseDto() {
      // GIVEN
      // ... id
      val id = 1L;
      val user = UserMother.complete().build();
      given(userRepository.findById(same(id))).willReturn(Optional.of(user));
      // ... ReplaceUserRequestDto
      val requestDto = ReplaceUserRequestDtoMother.complete().build();
      // ... CurrentUserService's logged-in User is equal to User
      val loggedInUser = user;
      given(currentUserService.getLoggedInUser()).willReturn(loggedInUser);
      // ... UserRepository fails to find User by oib
      given(userRepository.findByOib(same(user.getOib()))).willReturn(Optional.of(user));
      // ... UserMapper successfully partially updates User with ReplaceUserRequestDto
      given(userMapper.partialUpdate(same(user), same(requestDto))).willReturn(user);
      // ... UserRepository successfully saves User
      given(userRepository.save(same(user))).willReturn(user);
      // ... ConversionService successfully converts from User to UserResponseDto
      val expectedResponseDto = UserResponseDtoMother.complete().build();
      given(conversionService.convert(same(user), same(UserResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateUserById is called
      val responseDto = userService.updateUserById(id, requestDto);

      // THEN
      // ... UserResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

}
