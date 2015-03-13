package com.floorcrasher.mappers;

import org.springframework.beans.factory.annotation.Autowired;

public interface LoginMapper {
	
	public String getPasshashForUsername(String username);
}
