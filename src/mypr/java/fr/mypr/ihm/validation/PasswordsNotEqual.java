package fr.mypr.ihm.validation;

import javax.validation.*;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordsNotEqualValidator.class)
@Documented
public @interface PasswordsNotEqual {

    String message() default "PasswordsNotEqual";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordFieldName() default "";

    String passwordVerificationFieldName() default "";
}
