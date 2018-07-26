package com.gildedrose.expansion.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gildedrose.expansion.ApplicationTests;
import com.gildedrose.expansion.model.User;

public class UserRepositoryTests extends ApplicationTests{
	
	@Test
	public void testAddAndDeleteAppUserTests(){
		
		User admin=new User("testAdmin", "pass");
		List<String> adminAuthority=new ArrayList<>();
		adminAuthority.add("ADMIN");
		adminAuthority.add("USER");
		admin.setAuthority(adminAuthority);
		
		userRepo.saveAndFlush(admin);
		
		User rAdmin=userRepo.findByUsernameAndPassword("testAdmin", "pass");
		Assert.assertTrue(rAdmin!=null);
		Assert.assertEquals("testAdmin", rAdmin.getUsername());
		Assert.assertEquals("pass", rAdmin.getPassword());
		Assert.assertEquals("ADMIN", rAdmin.getAuthority().get(0));
		Assert.assertEquals("USER", rAdmin.getAuthority().get(1));
	}

}
