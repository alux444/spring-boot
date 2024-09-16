package com.example.full_rest_application;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_order")
public class Order {
  
  private @Id @GeneratedValue Long id;

  private String description;
  private Status status;

  Order() {}

  Order(String desc, Status status) {
    this.description = desc;
    this.status = status;
  }

  public Long getId() {
    return this.id;
  }

  public String getDescription() {
    return this.description;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setDescription(String desc) {
    this.description = desc;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Order)) {
      return false;
    }

    Order order = (Order) o;
    return Objects.equals(this.id, order.id)
    && Objects.equals(this.description, order.description)
    && Objects.equals(this.status, order.status);
  }

  @Override
  public String toString() {
    return "Order{" + 
    "id=" + this.id +
    ",description=" + this.description +
    ",status=" + this.status + "}\n";
  }
}
