package com.floorcrasher.enums;

public enum Gender {
	MALE("MALE"),
	FEMALE("FEMALE"),
	OTHER("OTHER")
	;
	
	private final String gender;
	
	private Gender(final String gender){
		this.gender = gender;
	}
	
	@Override
	public String toString(){
		return gender;
	}
}
