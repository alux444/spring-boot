package com.example.full_rest_application;

public class OrderNotFoundException extends RuntimeException {
  
  OrderNotFoundException(Long id) {
    super("Could not find order " + id);
  }
}
