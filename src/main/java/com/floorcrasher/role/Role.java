package com.floorcrasher.role;

public enum Role {
	ROLE_USER("ROLE_USER", "A normal user.", true),
	;
	
	private String id;
	private String description;
	private boolean active;
	
	Role(String roleId, String description, boolean active){
		this.id = roleId;
		this.description = description;
		this.active = active;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public boolean isActive(){
		return this.active;
	}
	
	public String toString(){
		return this.getId();
	}
}
