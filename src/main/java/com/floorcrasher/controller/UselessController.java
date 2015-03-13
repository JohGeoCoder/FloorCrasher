package com.floorcrasher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.floorcrasher.model.UselessObject;

@Controller
public class UselessController {

	@RequestMapping(value="/useless", method=RequestMethod.GET)
	public @ResponseBody UselessObject uselessMethod(UselessObject obj){
		
		return obj;
	}
	
}
