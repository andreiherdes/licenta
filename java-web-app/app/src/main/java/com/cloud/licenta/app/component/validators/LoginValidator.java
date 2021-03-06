package com.cloud.licenta.app.component.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cloud.licenta.app.model.Login;
import com.cloud.licenta.app.utils.ValidationUtilityClass;

@Component
public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Login.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {
		Login login = (Login) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "password.empty");

		if (!ValidationUtilityClass.isEmailValid(login.getEmail())) {
			e.reject("email", "email.notvalid");
		}
	}

}
