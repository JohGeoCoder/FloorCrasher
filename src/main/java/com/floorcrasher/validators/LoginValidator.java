package com.floorcrasher.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.floorcrasher.model.UserLogin;

@Component
public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserLogin.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Password required");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "Username required");
	}

}
