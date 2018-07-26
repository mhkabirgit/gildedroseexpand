package com.gildedrose.expansion.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gildedrose.expansion.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	public User findByUsernameIgnoreCase(String username);
	public User findByUsernameAndPassword(String username, String password);

}
