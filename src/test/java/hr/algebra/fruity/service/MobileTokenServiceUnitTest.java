package hr.algebra.fruity.service;

import hr.algebra.fruity.exception.InvalidMobileTokenException;
import hr.algebra.fruity.model.MobileToken;
import hr.algebra.fruity.repository.MobileTokenRepository;
import hr.algebra.fruity.service.impl.MobileTokenServiceImpl;
import hr.algebra.fruity.utils.mother.model.MobileTokenMother;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("MobileToken Unit Test")
@SuppressWarnings("static-access")
public class MobileTokenServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private MobileTokenServiceImpl mobileTokenService;

  @Mock
  private MobileTokenRepository mobileTokenRepository;

  @Nested
  @DisplayName("... WHEN createMobileToken is called")
  public class WHEN_createMobileToken {

    @Test
    @DisplayName("GIVEN void " +
      "... THEN MobileToken")
    public void GIVEN_void_THEN_MobileToken() {
      // GIVEN
      // ... MobileTokenRepository will successfully save MobileToken
      val expectedMobileToken = MobileTokenMother.complete().build();
      given(mobileTokenRepository.save(any(MobileToken.class))).willReturn(expectedMobileToken);

      // WHEN
      // ... createMobileToken is called
      val mobileToken = mobileTokenService.createMobileToken();

      // THEN
      // ... MobileToken
      and.then(mobileToken)
        .isNotNull()
        .isEqualTo(expectedMobileToken);
    }

  }

  @Nested
  @DisplayName("... WHEN verifyMobileToken is called")
  public class WHEN_verifyMobileToken {

    @Test
    @DisplayName("GIVEN invalid UUID " +
      "... THEN InvalidMobileTokenException is thrown")
    public void GIVEN_invalidUUID_THEN_InvalidMobileTokenException() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... MobileTokenRepository fails to find MobileToken by UUID
      given(mobileTokenRepository.findByUuid(same(uuid))).willReturn(Optional.empty());

      // WHEN
      // ... verifyMobileToken is called
      when(() -> mobileTokenService.verifyMobileToken(uuid));

      // THEN
      // ... InvalidMobileTokenException is thrown
      and.then(caughtException())
        .isInstanceOf(InvalidMobileTokenException.class)
        .hasMessage(InvalidMobileTokenException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN UUID " +
      "... THEN MobileToken is returned")
    public void GIVEN_UUID_THEN_MobileToken() {
      // GIVEN
      // ... UUID
      val uuid = UUID.randomUUID();
      // ... MobileTokenRepository successfully finds MobileToken by UUID
      val expectedMobileToken = MobileTokenMother.complete().build();
      given(mobileTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedMobileToken));

      // WHEN
      // ... verifyMobileToken is called
      val mobileToken = mobileTokenService.verifyMobileToken(uuid);

      // THEN
      // ... MobileToken is returned
      and.then(mobileToken)
        .isNotNull()
        .isEqualTo(expectedMobileToken);
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN InvalidMobileTokenException is thrown")
    public void GIVEN_invalidId_THEN_InvalidMobileTokenException() {
      // GIVEN
      // ... invalid id
      val uuid = UUID.randomUUID();
      given(mobileTokenRepository.findByUuid(same(uuid))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> mobileTokenService.getMobileTokenByUUID(uuid));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(InvalidMobileTokenException.class)
        .hasMessage(InvalidMobileTokenException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN MobileToken is returned")
    public void GIVEN_id_THEN_MobileToken() {
      // GIVEN
      // ... id
      val uuid = UUID.randomUUID();
      val expectedMobileToken = MobileTokenMother.complete().build();
      given(mobileTokenRepository.findByUuid(same(uuid))).willReturn(Optional.of(expectedMobileToken));

      // WHEN
      // ... getById is called
      val returnedMobileToken = mobileTokenService.getMobileTokenByUUID(uuid);

      // THEN
      // ... MobileTokenResponseDto is returned
      and.then(returnedMobileToken)
        .isNotNull()
        .isEqualTo(expectedMobileToken);
    }

  }

}