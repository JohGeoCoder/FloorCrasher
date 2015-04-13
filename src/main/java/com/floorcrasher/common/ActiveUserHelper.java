package com.floorcrasher.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ActiveUserHelper {

	public static Authentication getActiveUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}
}
