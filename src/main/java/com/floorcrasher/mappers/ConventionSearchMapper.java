package com.floorcrasher.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Options;

import com.floorcrasher.model.Convention;
import com.floorcrasher.model.ConventionSearch;

public interface ConventionSearchMapper {
	
	@Options(useGeneratedKeys=true, keyProperty="id", flushCache=true, keyColumn="id")
	public List<Convention> searchConventions(ConventionSearch search);
}
