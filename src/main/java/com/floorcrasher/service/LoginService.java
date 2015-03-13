package com.floorcrasher.service;

import com.floorcrasher.model.UserLogin;

public interface LoginService {

	boolean isLoginValid(UserLogin userLogin);
	
}
