package com.flight.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Constraint(validatedBy = SortOrderValidator.class)
@Target( {ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * SortOrderConstraint annotation to build custom annotation for sorting order inputs 
 * */
public @interface SortOrderValidation {

	 String message() default "Sorting Order Should be ASc or DESC only";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
}
