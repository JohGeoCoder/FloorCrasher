package com.floorcrasher.common;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CrackStationPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		String hash = null;
		try {
			hash = PasswordHash.createHash(rawPassword.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			hash = null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			hash = null;
		}
		
		return hash;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String correctHash) {
		boolean isValid = false;
		try {
			isValid = PasswordHash.validatePassword(rawPassword.toString(), correctHash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isValid = false;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isValid = false;
		}
		
		return isValid;
	}

}
