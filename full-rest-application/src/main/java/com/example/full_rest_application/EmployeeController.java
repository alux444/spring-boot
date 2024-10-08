package com.example.full_rest_application;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class EmployeeController {

  private final EmployeeRepository repo;
  private final EmployeeModelAssembler assembler;

  public EmployeeController(EmployeeRepository repo, EmployeeModelAssembler assembler) {
    this.repo = repo;
    this.assembler = assembler;
  }

  @GetMapping("/employees")
  CollectionModel<EntityModel<Employee>> getAllEmployees() {

    List<EntityModel<Employee>> employees = repo.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(employees,
        linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
  }

  @PostMapping("/employees")
  ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

    EntityModel<Employee> entityModel = assembler.toModel(newEmployee);

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @GetMapping("/employees/{id}")
  EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = repo.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
    return assembler.toModel(employee);
  }

  @PutMapping("/employees/{id}")
  ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    Employee updatedEmployee = repo.findById(id)
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return repo.save(employee);
        })
        .orElseGet(() -> {
          return repo.save(newEmployee);
        });

    EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/employees/{id}")
  ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
