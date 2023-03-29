package hr.algebra.fruity.service;

import hr.algebra.fruity.exception.InvalidRegistrationTokenException;
import hr.algebra.fruity.exception.RegistrationTokenAlreadyConfirmedException;
import hr.algebra.fruity.exception.RegistrationTokenExpiredException;
import hr.algebra.fruity.model.RegistrationToken;
import hr.algebra.fruity.properties.RegistrationTokenProperties;
import hr.algebra.fruity.repository.RegistrationTokenRepository;
import hr.algebra.fruity.service.impl.RegistrationTokenServiceImpl;
import hr.algebra.fruity.utils.mother.model.RegistrationTokenMother;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegistrationTokenService Unit Test")
@SuppressWarnings("static-access")
public class RegistrationTokenServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private RegistrationTokenServiceImpl registrationTokenService;

  @Mock
  private RegistrationTokenProperties registrationTokenProperties;

  @Mock
  private RegistrationTokenRepository registrationTokenRepository;

  @Nested
  @DisplayName("... WHEN createRegistrationToken is called")
  public class WHEN_createRegistrationToken {

    @Test
    @DisplayName("GIVEN void " +
      "... THEN RegistrationToken")
    void GIVEN_void_THEN_RegistrationToken() {
      // GIVEN
      // ... RegistrationTokenProperties will successfully contain validityDurationInMs property
      val validityDurationInMs = Duration.ofMillis(6000);
      given(registrationTokenProperties.validityDurationInMs()).willReturn(validityDurationInMs);
      // ... RegistrationTokenRepository will successfully save RegistrationToken
      val expectedRegistrationToken = RegistrationTokenMother.complete().build();
      given(registrationTokenRepository.save(any(RegistrationToken.class))).willReturn(expectedRegistrationToken);

      // WHEN
      // ... createRegistrationToken is called
      val registrationToken = registrationTokenService.createRegistrationToken();

      // THEN
      // ... RegistrationToken
      and.then(registrationToken)
        .isNotNull()
        .isEqualTo(expectedRegistrationToken);
    }

  }

  @Nested
  @DisplayName("... WHEN confirmRegistrationToken")
  public class WHEN_confirmRegistrationToken {

    @Test
    @DisplayName("GIVEN invalid UUID " +
      "... THEN InvalidRegistrationTokenException is thrown")
    public void GIVEN_invalidUUID_THEN_InvalidRegistrationTokenException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository fails to find RegistrationToken by UUID
      given(registrationTokenRepository.findByUuid(same(uuid))).willReturn(Optional.empty());

      // WHEN
      // ... confirmRegistrationToken is called
      when(() -> registrationTokenService.confirmRegistrationToken(uuid));

      // THEN
      // ... InvalidRegistrationTokenException is thrown
      and.then(caughtException())
        .isInstanceOf(InvalidRegistrationTokenException.class)
        .hasMessage(InvalidRegistrationTokenException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID and confirmed RegistrationToken " +
      "... THEN RegistrationTokenAlreadyConfirmedException is thrown")
    public void GIVEN_UUIDAndConfirmed_THEN_RegistrationTokenAlreadyConfirmedException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID which is confirmed
      val registrationToken = RegistrationTokenMother.complete().confirmDateTime(LocalDateTime.now()).build();
      given(registrationTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(registrationToken));

      // WHEN
      // ... confirmRegistrationToken is called
      when(() -> registrationTokenService.confirmRegistrationToken(uuid));

      // THEN
      // ... RegistrationTokenAlreadyConfirmedException is thrown
      and.then(caughtException())
        .isInstanceOf(RegistrationTokenAlreadyConfirmedException.class)
        .hasMessage(RegistrationTokenAlreadyConfirmedException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID and expired RegistrationToken " +
      "... THEN RegistrationTokenExpiredException is thrown")
    public void GIVEN_UUIDAndExpired_THEN_RegistrationTokenExpiredException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID which is expired
      val registrationToken = RegistrationTokenMother.complete()
        .createDateTime(LocalDateTime.now().minusMinutes(30))
        .expireDateTime(LocalDateTime.now().minusMinutes(15))
        .build();
      given(registrationTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(registrationToken));

      // WHEN
      // ... confirmRegistrationToken is called
      when(() -> registrationTokenService.confirmRegistrationToken(uuid));

      // THEN
      // ... RegistrationTokenExpiredException is thrown
      and.then(caughtException())
        .isInstanceOf(RegistrationTokenExpiredException.class)
        .hasMessage(RegistrationTokenExpiredException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID " +
      "... THEN RegistrationToken is returned")
    public void GIVEN_UUID_THEN_RegistrationToken() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID
      val expectedRegistrationToken = RegistrationTokenMother.complete().build();
      given(registrationTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedRegistrationToken));
      // ... RegistrationTokenRepository successfully saves RegistrationToken
      given(registrationTokenRepository.save(any(RegistrationToken.class))).willReturn(expectedRegistrationToken);

      // WHEN
      // ... confirmRegistrationToken is called
      val registrationToken = registrationTokenService.confirmRegistrationToken(uuid);

      // THEN
      // ... RegistrationToken is returned
      and.then(registrationToken)
        .isNotNull()
        .isEqualTo(expectedRegistrationToken);
      and.then(registrationToken.isConfirmed()).isTrue();
    }

  }

  @Nested
  @DisplayName("... WHEN refreshRegistrationToken")
  public class WHEN_refreshRegistrationToken {

    @Test
    @DisplayName("GIVEN invalid UUID" +
      "... THEN InvalidRegistrationTokenException is thrown")
    public void GIVEN_invalidUUID_THEN_InvalidRegistrationTokenException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository fails to find RegistrationToken by UUID
      given(registrationTokenRepository.findByUuid(same(uuid))).willReturn(Optional.empty());

      // WHEN
      // ... resendRegistrationToken is called
      when(() -> registrationTokenService.refreshRegistrationToken(uuid));

      // THEN
      // ... InvalidRegistrationTokenException is thrown
      and.then(caughtException())
        .isInstanceOf(InvalidRegistrationTokenException.class)
        .hasMessage(InvalidRegistrationTokenException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID" +
      "... THEN RegistrationToken is returned")
    public void GIVEN_UUID_THEN_RegistrationToken() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RegistrationTokenRepository successfully finds RegistrationToken by UUID
      val expectedRegistrationToken = RegistrationTokenMother.complete().build();
      given(registrationTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedRegistrationToken));
      // ... RegistrationTokenRepository successfully saves RegistrationToken
      given(registrationTokenRepository.save(any(RegistrationToken.class))).willReturn(expectedRegistrationToken);

      // WHEN
      // ... resendRegistrationToken is called
      val registrationToken = registrationTokenService.refreshRegistrationToken(uuid);

      // THEN
      // ... RegistrationToken is returned
      then(registrationTokenProperties).should(times(1)).validityDurationInMs();
      and.then(registrationToken)
        .isNotNull()
        .isEqualTo(expectedRegistrationToken);
      and.then(registrationToken.getConfirmDateTime()).isNull();
    }

  }

}
