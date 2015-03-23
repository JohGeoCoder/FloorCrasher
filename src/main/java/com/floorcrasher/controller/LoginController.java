package com.floorcrasher.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.floorcrasher.common.ViewConstants;
import com.floorcrasher.model.UserLogin;
import com.floorcrasher.service.LoginService;
import com.floorcrasher.validators.LoginValidator;

@Controller
public class LoginController {
	
	@Autowired
	private LoginValidator loginValidator;
	
	@Autowired 
	private LoginService loginService;
	
	private static final String TRUE = "true";

	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.setValidator(loginValidator);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login(UserLogin userLogin, HttpServletRequest request, Model model){
		ModelAndView mv = new ModelAndView();
		String isRegistered = request.getParameter("registered");
		if(TRUE.equals(isRegistered)){
			mv.addObject("registrationSuccess", "You have successfully registered and may now log in.");
		}
		mv.setViewName(ViewConstants.VIEW_LOGIN);
		return mv;
	}
}
