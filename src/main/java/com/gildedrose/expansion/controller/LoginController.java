package com.gildedrose.expansion.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gildedrose.expansion.model.User;
import com.gildedrose.expansion.service.UserService;

/**
 * Login controller to provide login form and handle login request.
 *  
 * @author Humayun Kabir
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManagerBean;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String index(Model model, HttpServletResponse response, @RequestParam(required=false) String error){
		
		if(error!=null){
			response.setStatus(401); //Unauthorized Request
		}
		
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody HttpServletResponse authenticated(	@RequestParam("username") String username,
															@RequestParam("password") String password,
															HttpServletResponse response){
		if(username==null ||username.isEmpty()){
			response.setStatus(400);  //Bad Request
		}
		else{
			if(!userService.userExists(username)){
				response.setStatus(401); //Unauthorized Request
			}
			else{
				
				UserDetails userDetails=userService.getUser(username);
				User user=new User(userDetails.getUsername(), userDetails.getPassword());
				user.setEnabled(userDetails.isEnabled());
				authenticationManagerBean.authenticate(user);
				response.setStatus(200); //OK
			}
		}
		
		return response;
	}

}
