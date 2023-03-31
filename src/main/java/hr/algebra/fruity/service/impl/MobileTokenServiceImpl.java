package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.exception.InvalidMobileTokenException;
import hr.algebra.fruity.model.MobileToken;
import hr.algebra.fruity.repository.MobileTokenRepository;
import hr.algebra.fruity.service.MobileTokenService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MobileTokenServiceImpl implements MobileTokenService {

  private final MobileTokenRepository mobileTokenRepository;

  @Override
  @Transactional
  public MobileToken createMobileToken() {
    val mobileToken = MobileToken.builder().build();

    return mobileTokenRepository.save(mobileToken);
  }

  @Override
  public MobileToken verifyMobileToken(UUID uuid) {
    return getMobileToken(uuid);
  }

  private MobileToken getMobileToken(UUID uuid) {
    return mobileTokenRepository.findByUuid(uuid)
      .orElseThrow(InvalidMobileTokenException::new);
  }


}
