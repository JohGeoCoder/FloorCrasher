package com.floorcrasher.mappers;

import org.apache.ibatis.annotations.Options;

import com.floorcrasher.model.UserRegistration;

public interface RegistrationMapper {
	
	@Options(useGeneratedKeys=true, keyProperty="id", flushCache=true, keyColumn="id")
	public void insertRegistration(UserRegistration reg);
	public void registerUserRole(UserRegistration reg);
	public UserRegistration getRegistrationByUsername(String username);
	public UserRegistration getRegistrationByEmail(String email);
}
