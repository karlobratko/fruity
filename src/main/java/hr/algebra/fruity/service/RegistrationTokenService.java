package hr.algebra.fruity.service;

import hr.algebra.fruity.model.RegistrationToken;
import java.util.UUID;

public interface RegistrationTokenService {

  RegistrationToken createRegistrationToken();

  RegistrationToken confirmRegistrationToken(UUID uuid);

  RegistrationToken refreshRegistrationToken(UUID uuid);

}
