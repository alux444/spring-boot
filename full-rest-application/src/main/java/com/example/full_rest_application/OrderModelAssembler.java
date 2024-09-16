package com.example.full_rest_application;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
  
  @Override
  public EntityModel<Order> toModel(Order order) {

    EntityModel<Order> orderModel = EntityModel.of(order,
      linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
      linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
  
      if (order.getStatus() == Status.IN_PROGRESS) {
        orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));
        orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId())).withRel("complete"));
      }

      return orderModel;
  }
}
