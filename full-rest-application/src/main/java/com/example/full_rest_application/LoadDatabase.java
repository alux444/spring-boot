package com.example.full_rest_application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
  
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository empRepo, OrderRepository orderRepo) {

    return args -> {
      Employee timmy = new Employee("Timmy", "Bob", "Software engineer");
      Employee johnny = new Employee("Johnny", "Den", "Truck driver");
      Order macbook = new Order("Macbook", Status.COMPLETED);
      Order iphone = new Order("iPhone 16", Status.IN_PROGRESS);

      log.info("Loading existing data..." + empRepo.save(timmy));
      log.info("Loading existing data..." + empRepo.save(johnny));
      log.info("Loading existing data..." + orderRepo.save(macbook));
      log.info("Loading existing data..." + orderRepo.save(iphone));
    };
  }
}
