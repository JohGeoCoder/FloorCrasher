package com.floorcrasher.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.floorcrasher.model.UserLogin;

@Controller
public class LogoutController {

	
	@RequestMapping(value="/fc_logout", method=RequestMethod.GET)
	public ModelAndView login(UserLogin userLogin, HttpServletRequest request, Model model){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fc_logout");
		return mv;
	}
}
