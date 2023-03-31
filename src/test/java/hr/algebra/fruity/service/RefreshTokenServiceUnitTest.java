package hr.algebra.fruity.service;

import hr.algebra.fruity.exception.InvalidRefreshTokenException;
import hr.algebra.fruity.exception.RefreshTokenExpiredException;
import hr.algebra.fruity.model.RefreshToken;
import hr.algebra.fruity.properties.JwtProperties;
import hr.algebra.fruity.repository.RefreshTokenRepository;
import hr.algebra.fruity.service.impl.RefreshTokenServiceImpl;
import hr.algebra.fruity.utils.mother.model.RefreshTokenMother;
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
@DisplayName("RefreshToken Unit Test")
@SuppressWarnings("static-access")
public class RefreshTokenServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private RefreshTokenServiceImpl refreshTokenService;

  @Mock
  private JwtProperties jwtProperties;

  @Mock
  private RefreshTokenRepository refreshTokenRepository;

  @Nested
  @DisplayName("... WHEN createRefreshToken is called")
  public class WHEN_createRefreshToken {

    @Test
    @DisplayName("GIVEN void " +
      "... THEN RefreshToken")
    void GIVEN_void_THEN_RefreshToken() {
      // GIVEN
      // ... JwtProperties will successfully contain validityDurationInMs property
      val validityDurationInMs = Duration.ofMillis(6000);
      given(jwtProperties.refreshValidityDurationInMs()).willReturn(validityDurationInMs);
      // ... RefreshTokenRepository will successfully save RefreshToken
      val expectedRefreshToken = RefreshTokenMother.complete().build();
      given(refreshTokenRepository.save(any(RefreshToken.class))).willReturn(expectedRefreshToken);

      // WHEN
      // ... createRefreshToken is called
      val refreshToken = refreshTokenService.createRefreshToken();

      // THEN
      // ... RefreshToken
      then(jwtProperties).should(times(1)).refreshValidityDurationInMs();
      and.then(refreshToken)
        .isNotNull()
        .isEqualTo(expectedRefreshToken);
    }

  }

  @Nested
  @DisplayName("... WHEN refreshRefreshToken is called")
  public class WHEN_refreshRefreshToken {

    @Test
    @DisplayName("GIVEN invalid UUID " +
      "... THEN InvalidRefreshTokenException is thrown")
    public void GIVEN_invalidUUID_THEN_InvalidRefreshTokenException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RefreshTokenRepository fails to find RefreshToken by UUID
      given(refreshTokenRepository.findByUuid(same(uuid))).willReturn(Optional.empty());

      // WHEN
      // ... confirmRefreshToken is called
      when(() -> refreshTokenService.refreshRefreshToken(uuid));

      // THEN
      // ... InvalidRefreshTokenException is thrown
      and.then(caughtException())
        .isInstanceOf(InvalidRefreshTokenException.class)
        .hasMessage(InvalidRefreshTokenException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID " +
      "... THEN RefreshToken is returned")
    public void GIVEN_UUID_THEN_RefreshToken() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RefreshTokenRepository successfully finds RefreshToken by UUID
      val expectedRefreshToken = RefreshTokenMother.complete().build();
      given(refreshTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedRefreshToken));
      // ... RefreshTokenRepository successfully saves RefreshToken
      given(refreshTokenRepository.save(any(RefreshToken.class))).willReturn(expectedRefreshToken);

      // WHEN
      // ... confirmRefreshToken is called
      val refreshToken = refreshTokenService.refreshRefreshToken(uuid);

      // THEN
      // ... RefreshToken is returned
      then(jwtProperties).should(times(1)).refreshValidityDurationInMs();
      and.then(refreshToken)
        .isNotNull()
        .isEqualTo(expectedRefreshToken);
    }

  }

  @Nested
  @DisplayName("... WHEN verifyRefreshToken is called")
  public class WHEN_verifyRefreshToken {

    @Test
    @DisplayName("GIVEN invalid UUID " +
      "... THEN InvalidRefreshTokenException is thrown")
    public void GIVEN_invalidUUID_THEN_InvalidRefreshTokenException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RefreshTokenRepository fails to find RefreshToken by UUID
      given(refreshTokenRepository.findByUuid(same(uuid))).willReturn(Optional.empty());

      // WHEN
      // ... verifyRefreshToken is called
      when(() -> refreshTokenService.verifyRefreshToken(uuid));

      // THEN
      // ... InvalidRefreshTokenException is thrown
      and.then(caughtException())
        .isInstanceOf(InvalidRefreshTokenException.class)
        .hasMessage(InvalidRefreshTokenException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID and expired RefreshToken " +
      "... THEN RefreshTokenExpiredException is thrown")
    public void GIVEN_UUIDAndExpired_THEN_RefreshTokenExpiredException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RefreshTokenRepository successfully finds RefreshToken by UUID which is expired
      val expectedRefreshToken = RefreshTokenMother.complete().expireDateTime(LocalDateTime.now().minusMinutes(1)).build();
      given(refreshTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedRefreshToken));

      // WHEN
      // ... verifyRefreshToken is called
      when(() -> refreshTokenService.verifyRefreshToken(uuid));

      // THEN
      // ... InvalidRefreshTokenException is thrown
      and.then(caughtException())
        .isInstanceOf(RefreshTokenExpiredException.class)
        .hasMessage(RefreshTokenExpiredException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID " +
      "... THEN RefreshToken is returned")
    public void GIVEN_UUID_THEN_RefreshToken() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... RefreshTokenRepository successfully finds RefreshToken by UUID
      val expectedRefreshToken = RefreshTokenMother.complete().build();
      given(refreshTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedRefreshToken));

      // WHEN
      // ... verifyRefreshToken is called
      val refreshToken = refreshTokenService.verifyRefreshToken(uuid);

      // THEN
      // ... RefreshToken is returned
      and.then(refreshToken)
        .isNotNull()
        .isEqualTo(expectedRefreshToken);
    }

  }

}
