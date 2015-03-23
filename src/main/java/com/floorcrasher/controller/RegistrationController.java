package com.floorcrasher.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.floorcrasher.common.ViewConstants;
import com.floorcrasher.enums.Gender;
import com.floorcrasher.model.UserRegistration;
import com.floorcrasher.role.Role;
import com.floorcrasher.service.RegistrationService;
import com.floorcrasher.validators.RegistrationValidator;

@Controller
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private RegistrationValidator registrationValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
		binder.setValidator(registrationValidator);
	}
	
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public ModelAndView register(UserRegistration userRegistration){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.VIEW_REGISTRATION);
		
		Gender[] genders = Gender.values();
		
		mv.addObject("genders", genders);
		
		return mv;
	}
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public ModelAndView register(@Valid UserRegistration registration, BindingResult result, Model model){
		ModelAndView mv = new ModelAndView();
		mv.addObject(ViewConstants.VIEW_REGISTRATION, registration);
		
		if(result.hasErrors()){
			Gender[] genders = Gender.values();
			mv.addObject("genders", genders);
			mv.setViewName(ViewConstants.VIEW_REGISTRATION);
			return mv;
		}
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ROLE_USER);
		registration.setRoles(roles);
		
		mv.addObject("regInfo", registration);
		
		try{
			registrationService.insertRegistration(registration);
		} catch(Exception e){
			mv.addObject("registrationError", "There was an error during registration. You have not been registered.");
			Gender[] genders = Gender.values();
			mv.addObject("genders", genders);
			mv.setViewName(ViewConstants.VIEW_REGISTRATION);
			return mv;
		}
		
		mv.setViewName("redirect:/fc_login?registered=true");
		return mv;
	}
}
