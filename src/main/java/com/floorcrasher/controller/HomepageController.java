package com.floorcrasher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.floorcrasher.common.ViewConstants;

@Controller
public class HomepageController {
	
	@RequestMapping(value="/homepage", method=RequestMethod.GET)
	public ModelAndView homepage(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.VIEW_HOMEPAGE);
		return mv;
	}
}
