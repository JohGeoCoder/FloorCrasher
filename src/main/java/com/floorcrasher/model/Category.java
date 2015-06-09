package com.floorcrasher.model;

public class Category {
	private Long id;
	private Long conventionId;
	private String keyword;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getConventionId() {
		return conventionId;
	}
	public void setConventionId(Long conventionId) {
		this.conventionId = conventionId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
