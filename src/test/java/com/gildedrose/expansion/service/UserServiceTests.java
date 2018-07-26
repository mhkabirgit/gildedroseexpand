package com.gildedrose.expansion.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.gildedrose.expansion.ApplicationTests;

public class UserServiceTests extends ApplicationTests{
	
	@AfterTransaction
	public void deleteTestUser(){
		userService.deleteUser(TEST_USERNAME);
	}
	
	@BeforeTransaction
	 public void createTestUser() {
		if (!userService.userExists(TEST_USERNAME)) {
			
			// create the test user.
			userService.createUser(TEST_USERNAME, TEST_USER_PASSWORD, Arrays.asList("USER"), true);
		}
	 }
	
	@Test
	public void testAuthenticationManager(){
		Assert.assertTrue(authenticationManagerBean!=null);
	}
	
	@Test
	public void testTestUser(){
		Assert.assertTrue(userService.userExists(TEST_USERNAME));
	}

}
