package com.gildedrose.expansion.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GildedRoseExceptionHandler {
	
	@ExceptionHandler({Exception.class, RuntimeException.class})
	public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Exception exception){
		
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("exception", exception);
		modelAndView.setViewName("/errors/exception");
		return modelAndView;
	}

}
