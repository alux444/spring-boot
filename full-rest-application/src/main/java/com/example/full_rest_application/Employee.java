package com.example.full_rest_application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.*;

@Entity
public class Employee {
  
  private @Id
  @GeneratedValue Long id;
  private String firstName;
  private String lastName;
  private String role;

  public Employee() {}

  public Employee(String firstName, String lastName, String role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.firstName + " " + this.lastName;
  }

  public String getRole() {
    return this.role;
  }

  public void setName(String name) {
    String[] names = name.split(" ");
    this.firstName = names[0];
    this.lastName = names[1];
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Employee)) {
      return false;
    }

    Employee emp = (Employee) o;
    return Objects.equals(this.id, emp.id)
    && Objects.equals(this.firstName, emp.firstName)
    && Objects.equals(this.lastName, emp.lastName)
    && Objects.equals(this.role, emp.role);
  }

  @Override
  public String toString() {
    return "Employee{" +
    "id=" + this.id + 
    ",firstName=" + this.firstName +
    ",lastName=" + this.lastName +
    ",role=" + this.role + "}\n";
  }
}
