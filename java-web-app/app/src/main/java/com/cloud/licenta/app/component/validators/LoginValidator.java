package com.cloud.licenta.app.component.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cloud.licenta.app.model.Login;

@Component
public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Login.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "password.empty");
	}

}
