package com.example.full_rest_application;

public class EmployeeNotFoundException extends RuntimeException {
  
  EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id + "\n");
  }
}
