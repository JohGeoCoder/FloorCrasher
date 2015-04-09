package com.floorcrasher.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.floorcrasher.common.PasswordHash;
import com.floorcrasher.mappers.LoginMapper;
import com.floorcrasher.model.UserLogin;
import com.floorcrasher.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public boolean isLoginValid(UserLogin userLogin) {
		if(userLogin == null){
			return false;
		}
		
		boolean result = false;
		
		try {
			String databasePasshash = loginMapper.getPasshashForUsername(userLogin.getUsername());
			if(databasePasshash != null){
				result = PasswordHash.validatePassword(userLogin.getPassword(), databasePasshash);
			}
		} catch (NoSuchAlgorithmException e) {
			result = false;
		} catch (InvalidKeySpecException e) {
			result = false;
		}
		
		return result;
	}
	
	@Override
	public String getUserPasshash(UserLogin userLogin){
		String passhash = null;
		
		if(userLogin != null){
			passhash = loginMapper.getPasshashForUsername(userLogin.getUsername());
		}
		
		return passhash;
	}
}
