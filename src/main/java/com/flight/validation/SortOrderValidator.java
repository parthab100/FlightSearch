package com.flight.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.flight.exceptions.InputValidationException;

public class SortOrderValidator implements ConstraintValidator<SortOrderValidation, String> {


	
	@Override
	public boolean isValid(String inputvalue, ConstraintValidatorContext context) {
		if (!StringUtils.isBlank(inputvalue)) {
			if ((inputvalue.equalsIgnoreCase("ASC") || (inputvalue.equalsIgnoreCase("DESC")))) {
				return true;
			}
			else {
				return false;
			}
		}
		return true;
	}

}
