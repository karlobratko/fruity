package hr.algebra.fruity.validator;

@FunctionalInterface
public interface Validator<T, W> {

  void validate(T target, W with);

}
