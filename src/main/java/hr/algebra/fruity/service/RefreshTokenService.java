package hr.algebra.fruity.service;

import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.RefreshToken;
import java.util.UUID;

public interface RefreshTokenService {

  RefreshToken createRefreshToken();

  RefreshToken refreshRefreshToken(UUID uuid);

  RefreshToken verifyRefreshToken(UUID uuid);

  RefreshToken getRefreshTokenByUUID(UUID uuid);

}
