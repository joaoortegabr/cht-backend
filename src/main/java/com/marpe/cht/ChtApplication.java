package com.marpe.cht;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class} )
public class ChtApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("pt", "BR"));
		SpringApplication.run(ChtApplication.class, args);
	}


}
