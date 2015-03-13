package com.floorcrasher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.floorcrasher.mappers.ConventionSearchMapper;
import com.floorcrasher.model.Convention;
import com.floorcrasher.model.ConventionSearch;
import com.floorcrasher.service.ConventionSearchService;

@Service("conventionSearchService")
public class ConventionSearchServiceImpl implements ConventionSearchService {
	
	@Autowired
	private ConventionSearchMapper conventionSearchMapper;

	@Override
	public List<Convention> searchConventions(ConventionSearch search) {
		return conventionSearchMapper.searchConventions(search);
	}
	
}
