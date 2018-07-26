package com.gildedrose.expansion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	
	@RequestMapping("/")
	public ModelAndView handleInternalRequest(){
		return new ModelAndView("home");
	}
	
	@RequestMapping("/home")
	public ModelAndView home(){
		return new ModelAndView("home");
	}
		
}
