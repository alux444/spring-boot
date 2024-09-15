package com.example.springapplication;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringapplicationApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLinerunner(ApplicationContext context) {
		return args -> {

			System.out.println("Check beans provided by spring boot");

			String[] beanNames = context.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}

}
