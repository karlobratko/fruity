package hr.algebra.fruity.validator;

@FunctionalInterface
public interface WithValidator<T, W> {

  void validate(T target, W with);

}
