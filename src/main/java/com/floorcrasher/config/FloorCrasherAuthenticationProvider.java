package com.floorcrasher.config;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.floorcrasher.common.PasswordHash;
import com.floorcrasher.model.UserLogin;
import com.floorcrasher.service.LoginService;

public class FloorCrasherAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		UserLogin loginInfo = new UserLogin();
		loginInfo.setUsername(name);
		loginInfo.setPassword(password);
		
		String passHash = loginService.getUserPasshash(loginInfo);
		
		try {
			if(PasswordHash.validatePassword(password, passHash)){
				List<GrantedAuthority> grantedAuths = new ArrayList<>();
	            grantedAuths.add(new SimpleGrantedAuthority("USER"));
	            return new UsernamePasswordAuthenticationToken(loginInfo, authentication, grantedAuths); 
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
