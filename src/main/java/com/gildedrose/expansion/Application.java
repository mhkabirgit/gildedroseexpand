package com.gildedrose.expansion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot Application for Gilded Rose Expansion Project.
 * 
 * @author Humayun Kabir
 *
 */
@SpringBootApplication
@EnableScheduling
public class Application {
	
	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(Application.class);
    	app.setShowBanner(false);
    	app.setApplicationContextClass(AnnotationConfigApplicationContext.class);
    	app.setApplicationContextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
    	app.setWebEnvironment(true);
    	app.setAddCommandLineProperties(true);
    	app.run(args);
	}

}
