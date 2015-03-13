package com.floorcrasher.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.floorcrasher.common.ConventionSearchParser;
import com.floorcrasher.model.Convention;
import com.floorcrasher.model.ConventionSearch;
import com.floorcrasher.model.ConventionSearchResult;
import com.floorcrasher.service.ConventionSearchService;

@Controller
public class ConventionController{
	
	@Autowired
	private ConventionSearchService conventionSearchService;
	
	@RequestMapping(value="/conventions", method=RequestMethod.GET)
	public ModelAndView testGet(Convention searchText){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("testConSearch");
		
		return mv;
	}
	
	@RequestMapping(value="/conventions", method=RequestMethod.POST)
	@ResponseBody
	public ConventionSearchResult getConventions(ConventionSearch search){
		
		ConventionSearchParser parser = new ConventionSearchParser(search.getSearchText());
		List<Convention> conventions = conventionSearchService.searchConventions(search);
		
		ConventionSearchResult result = new ConventionSearchResult();
		result.setConventions(conventions);
		if(parser.getStartDate() != null){
			result.setStartDate(new SimpleDateFormat("MM/dd/yyyy").format(parser.getStartDate()));
		}
		if(parser.getEndDate() != null){
			result.setEndDate(new SimpleDateFormat("MM/dd/yyyy").format(parser.getEndDate()));
		}
		result.setKeywords(parser.getKeywords());
		
		return result;
	}
}
