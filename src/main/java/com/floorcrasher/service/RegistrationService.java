package com.floorcrasher.service;

import com.floorcrasher.model.UserRegistration;

public interface RegistrationService {
	void insertRegistration(UserRegistration registration);
	boolean getRegistrationExistsByUsername(String username);
	boolean getRegistrationExistsByEmail(String email);
}
