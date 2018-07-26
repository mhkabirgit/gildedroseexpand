package com.gildedrose.expansion;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gildedrose.expansion.model.repository.InventoryRepository;
import com.gildedrose.expansion.model.repository.ItemRepository;
import com.gildedrose.expansion.model.repository.UserRepository;
import com.gildedrose.expansion.service.InventoryService;
import com.gildedrose.expansion.service.PaymentService;
import com.gildedrose.expansion.service.PriceService;
import com.gildedrose.expansion.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest ("server.port:8080")
@Transactional
public class ApplicationTests {
	
	public static final String TEST_USERNAME = "testuser";
	public static final String TEST_USER_PASSWORD = "passw0rd";
	
	public static final String TEST_ITEM_NAME = "TestItem";
	public static final String TEST_ITEM_DESCRIPTION = "Test Item Description";
	public static final int TEST_ITEM_PRICE = 100;
	public static final int TEST_ITEM_STOCK = 10;
	
	@Autowired
	protected Environment environment; 

	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected AuthenticationManager authenticationManagerBean;
		
	@Autowired
	protected UserRepository userRepo;
	
	@Autowired
	protected ItemRepository itemRepo;
	
	@Autowired
	protected InventoryRepository inventoryRepo;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected PriceService priceService;
	
	@Autowired 
	protected PaymentService paymentService;
	
	@Autowired
	protected InventoryService inventoryService;
	
	
	@Value("${profile.name}")
	private String profileName;
	
	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}
	
	
	/**
	 * This stub method is required to initialize SpringJUnit4ClassRunner.
	 */
	@Test
	public void contextLoads() {
		//Do nothing
	}	 
	
}
