package com.floorcrasher.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.floorcrasher.common.ActiveUserHelper;
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
