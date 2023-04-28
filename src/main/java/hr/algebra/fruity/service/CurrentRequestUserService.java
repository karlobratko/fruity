package hr.algebra.fruity.service;

import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.model.User;

public interface CurrentRequestUserService {

  User getUser();

  Long getUserId();

  Employee getEmployee();

  Long getEmployeeId();

}
