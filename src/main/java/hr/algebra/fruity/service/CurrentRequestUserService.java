package hr.algebra.fruity.service;

import hr.algebra.fruity.model.User;

@FunctionalInterface
public interface CurrentRequestUserService {

  User getUser();

}
