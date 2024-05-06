package hr.algebra.fruity.service;

import hr.algebra.fruity.model.Email;
import hr.algebra.fruity.model.Employee;
import java.util.UUID;

public interface EmailComposerService {

  Email composeConfirmRegistrationEmail(Employee employee, String registrationUrl, UUID registrationToken);

}
