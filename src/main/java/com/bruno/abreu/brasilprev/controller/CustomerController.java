package com.bruno.abreu.brasilprev.controller;

import com.bruno.abreu.brasilprev.model.Customer;
import com.bruno.abreu.brasilprev.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/customers")
@Api(tags = "Customer Controller", description = "Customer CRUD operations")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ApiOperation(value = "Create customer")
    public ResponseEntity<?> create(@Valid @RequestBody Customer customer){
        Customer newCustomer = customerService.create(customer);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(newCustomer.getId())
                        .toUri())
                .body(newCustomer);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Read customer")
    public ResponseEntity<?> read(@PathVariable("id") Long id){
        Customer customer = customerService.read(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update customer")
    public ResponseEntity<?> update(@Valid @RequestBody Customer customer, @PathVariable("id") Long id){
        customer.setId(id);
        Customer newCustomer = customerService.update(customer);
        return ResponseEntity
                .ok(newCustomer);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete customer")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Find all customers")
    public ResponseEntity<?> findAll(){
        List<Customer> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }
}
