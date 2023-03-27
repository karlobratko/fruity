package hr.algebra.fruity.service;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.User;

public interface CurrentUserService {

  User getLoggedInUser();

  Employee getLoggedInEmployee();

}
