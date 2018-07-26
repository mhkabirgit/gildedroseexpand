package com.gildedrose.expansion.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * User is used to model the user of the application.
 * 
 * @author Humayun Kabir
 *
 */
@Entity (name="users")
public class User implements Authentication{
		
	@Id
	@Column(unique=true)
	@NotNull(message="User name is required")
	private String username;
	
	@NotNull(message="Password is required")
	private String password;
	
	@NotNull(message="Enabled is required")
	private boolean enabled;
	
	@ElementCollection
	@CollectionTable(name="authorities", joinColumns=@JoinColumn(name="username"))
	private List<String> authority;
	
	@Transient
	private boolean authenticated;
	
	public User(){
		username="";
		password="";
	}
	
	public User(String username, String password){
		this.username=username;
		this.password=password;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	} 
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * @return the authority
	 */
	public List<String> getAuthority() {
		return authority;
	}
	

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(List<String> authority) {
		this.authority = authority;
	}

	@Override
	@JsonIgnore
	public String getName() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		for(String auth:authority){
			authorities.add(new SimpleGrantedAuthority(auth));
		}
		return authorities;
	}

	@Override
	@JsonIgnore
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated=authenticated;
		
	}

}
