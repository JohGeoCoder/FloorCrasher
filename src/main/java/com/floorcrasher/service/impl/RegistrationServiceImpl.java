package com.floorcrasher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.floorcrasher.mappers.RegistrationMapper;
import com.floorcrasher.model.UserRegistration;
import com.floorcrasher.service.RegistrationService;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	private RegistrationMapper registrationMapper;

	@Transactional
	public void insertRegistration(UserRegistration registration) {
		registrationMapper.insertRegistration(registration);
		registrationMapper.registerUserRole(registration);
	}

	public boolean getRegistrationExistsByUsername(String username) {
		UserRegistration profile = registrationMapper.getRegistrationByUsername(username);
		if(profile != null){
			return true;
		}
		
		return false;
	}

	public boolean getRegistrationExistsByEmail(String email) {
		UserRegistration profile = registrationMapper.getRegistrationByEmail(email);
		if(profile != null){
			return true;
		}
		
		return false;
	}	
}
