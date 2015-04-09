package com.floorcrasher.validators;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.floorcrasher.common.PasswordHash;
import com.floorcrasher.model.UserRegistration;
import com.floorcrasher.service.RegistrationService;

@Component
public class RegistrationValidator implements Validator {
	
	@Autowired
	private RegistrationService registrationService;

	@Override
	public boolean supports(Class<?> arg0) {
		return UserRegistration.class.equals(arg0);
	}

	@Override
	public void validate(Object obj, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "password.empty", "Password required");	
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "username.empty", "Username required");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty", "Email required");
		
		UserRegistration reg = (UserRegistration) obj;
		
		if(registrationService.getRegistrationExistsByEmail(reg.getEmail())){
			e.rejectValue("email", "email.emailExists", "An account using this email address already exists.");
		}
		
		if(registrationService.getRegistrationExistsByUsername(reg.getUsername())){
			e.rejectValue("username", "username.usernameExists", "This username is already in use");
		}
		
		try {
			reg.setPasshash(PasswordHash.createHash(reg.getPassword()));
		} catch (NoSuchAlgorithmException ex) {
			e.rejectValue("password", "password.hashError", "Error hashing the password");
		} catch (InvalidKeySpecException ex) {
			e.rejectValue("password", "password.hashError", "Error hashing the password");
		}
		
		if(StringUtils.isNotBlank(reg.getPassword()) && !reg.getPassword().equals(reg.getPasswordRepeat())){
			e.rejectValue("passwordRepeat", "passwordRepeat.noMatch", "Passwords must match");
		}
		
		if(StringUtils.isNotBlank(reg.getEmail()) && !reg.getEmail().equals(reg.getEmailRepeat())){
			e.rejectValue("emailRepeat", "emailRepeat.noMatch", "Emails must match");
		}
		
		if(reg.getGender() == null || StringUtils.isBlank(reg.getGender().toString())){
			e.rejectValue("gender", "gender.empty", "Please select a gender");
		}
		
		if(reg.getBirthDate() == null){
			e.rejectValue("birthDate", "birthDate.empty", "Please enter a birth date");
		}
	}
}
