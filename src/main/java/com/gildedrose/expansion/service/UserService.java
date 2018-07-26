package com.gildedrose.expansion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.gildedrose.expansion.model.User;
import com.gildedrose.expansion.model.repository.UserRepository;


/**
 * UserService to create, delete, and update users of the application
 * 
 * @author Humayun Kabir
 *
 */
@Service
public class UserService {
	
	@Autowired
	UserDetailsManager userDetailsManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	public boolean userExists(String username){
		return userDetailsManager.userExists(username);
	}
	
	public UserDetails getUser(String username){
		return userDetailsManager.loadUserByUsername(username);
	}
	
	public void deleteUser(String username){
		userDetailsManager.deleteUser(username);
	}
	
	public void createUser(String username, String password, List<String> roles, boolean enabled){
		if(!userExists(username)){
			List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
			for(String role: roles){
				authorities.add(new SimpleGrantedAuthority(role));
			}
			UserDetails newUser= new org.springframework.security.core.userdetails.User(
					username, passwordEncoder.encode(password), enabled, true, true, true, authorities);
			userDetailsManager.createUser(newUser);
		}
	}
	
	public void updateUser(String username, String password, List<String> roles, boolean enabled){
		if(userExists(username)){
			List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
			for(String role: roles){
				authorities.add(new SimpleGrantedAuthority(role));
			}
			
			if(password==null||password.isEmpty()){
				password=getUser(username).getPassword();
			}
			else{
				password=passwordEncoder.encode(password);
			}
			UserDetails updatedUser= new org.springframework.security.core.userdetails.User(
					username, password, enabled, true, true, true, authorities);
			userDetailsManager.updateUser(updatedUser);
		}
	}
	
	public List<User> getAllUsers(){
		List<User> users=userRepo.findAll();
		for(User user: users){
			if(userExists(user.getUsername())){
				List<String> roles=new ArrayList<String>();
				UserDetails userDetails=getUser(user.getUsername());
				for(GrantedAuthority auth: userDetails.getAuthorities()){
					roles.add(auth.getAuthority());
				}
				user.setAuthority(roles);
			}
		}
		return users;
	}

}
