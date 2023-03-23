package hr.algebra.fruity.constraints;

import hr.algebra.fruity.validator.UniqueOibValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueOibValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueOib {

  String message() default "OIB već postoji i nije jedinstven.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
