package com.example.reactive_web_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ReactiveWebServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ReactiveWebServiceApplication.class, args);

		GreetingClient greetingClient = context.getBean(GreetingClient.class);
		//block here or JVM might exit before message is locked
		System.out.println(">> message = " + greetingClient.getMessage().block());
	}

}
