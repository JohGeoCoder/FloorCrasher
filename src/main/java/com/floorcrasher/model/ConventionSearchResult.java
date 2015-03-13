package com.floorcrasher.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ConventionSearchResult {
	private List<Convention> conventions;
	private String startDate;
	private String endDate;
	private Set<String> keywords;
	
	public List<Convention> getConventions() {
		return conventions;
	}
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Set<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
}
