package com.cloud.licenta.app.component.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cloud.licenta.app.model.Contact;
import com.cloud.licenta.app.utils.ValidationUtilityClass;

@Component
public class ContactValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Contact.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {
		Contact contact = (Contact) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "text", "text.empty");

		if (!ValidationUtilityClass.isEmailValid(contact.getEmail())) {
			e.reject("email", "email.empty");
		}
	}

}
