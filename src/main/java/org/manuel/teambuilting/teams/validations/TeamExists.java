package org.manuel.teambuilting.teams.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Manuel on 12/12/2016.
 */
@Constraint(validatedBy = {TeamExistsValidator.class})
@Documented
@Target({ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TeamExists {

    String message() default "{The team does not exist}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
