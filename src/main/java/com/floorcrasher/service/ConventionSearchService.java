package com.floorcrasher.service;

import java.util.List;

import com.floorcrasher.model.Convention;
import com.floorcrasher.model.ConventionSearch;

public interface ConventionSearchService {
	List<Convention> searchConventions(ConventionSearch search);
}
