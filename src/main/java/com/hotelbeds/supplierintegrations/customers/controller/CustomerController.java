package com.hotelbeds.supplierintegrations.customers.controller;

import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @GetMapping
  public ResponseEntity<CustomerDTO> getCustomerByUserName(@PathVariable String userName) {
    return ResponseEntity.ok(new CustomerDTO());
  }

  @PostMapping
  public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customer) {
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userName}")
        .buildAndExpand(customer.getUserName()).toUri();

    return ResponseEntity.created(location).build();

  }

}
