package com.retotcs.demodevops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.retotcs.demodevops")
public class DemodevopsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemodevopsApplication.class, args);
	}

}
