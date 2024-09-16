package com.example.full_rest_application;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderRepository repo;
  private final OrderModelAssembler assembler;

  OrderController(OrderRepository repo, OrderModelAssembler assembler) {
    this.repo = repo;
    this.assembler = assembler;
  }

  @GetMapping("/orders")
  CollectionModel<EntityModel<Order>> getAllOrders() {

    List<EntityModel<Order>> orders = repo.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(orders,
        linkTo(methodOn(OrderController.class)
            .getAllOrders())
            .withSelfRel());
  }

  @GetMapping("/orders/{id}")
  EntityModel<Order> getOrderById(@PathVariable Long id) {

    Order order = repo.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));

    return assembler.toModel(order);
  }

  @PostMapping("/orders")
  ResponseEntity<EntityModel<Order>> createOrder(@RequestBody Order order) {

    order.setStatus(Status.IN_PROGRESS);
    Order newOrder = repo.save(order);

    return ResponseEntity
        .created(linkTo(methodOn(OrderController.class)
            .getOrderById(newOrder.getId()))
            .toUri())
        .body(assembler.toModel(newOrder));
  }

  @DeleteMapping("/orders/{id}/cancel")
  ResponseEntity<?> cancelOrder(@PathVariable Long id) {

    Order order = repo.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.CANCELLED);
      return ResponseEntity.ok(assembler.toModel(repo.save(order)));
    }

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .header(HttpHeaders.CONTENT_TYPE,
            MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create()
            .withTitle("Method not allowed")
            .withDetail("Can't cancel an order with status: " + order.getStatus()));
  }

  @PutMapping("/orders/{id}/complete")
  ResponseEntity<?> completeOrder(@PathVariable Long id) {

    Order order = repo.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.COMPLETED);
      return ResponseEntity.ok(assembler.toModel(repo.save(order)));
    }

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .header(HttpHeaders.CONTENT_TYPE,
            MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create()
            .withTitle("Method not allowed")
            .withDetail("Can't complete an order that has status " + order.getStatus()));
  }
}
