package hr.algebra.fruity.service;

import hr.algebra.fruity.model.MobileToken;
import java.util.UUID;

public interface MobileTokenService {

  MobileToken createMobileToken();

  MobileToken verifyMobileToken(UUID uuid);

}
