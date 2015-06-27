package fr.mypr.user.registration.validation;

import javax.validation.*;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordsNotEmptyValidator.class)
@Documented
public @interface PasswordsNotEmpty {

    String message() default "PasswordsNotEmpty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String passwordFieldName() default "";

    String passwordVerificationFieldName() default "";
}
