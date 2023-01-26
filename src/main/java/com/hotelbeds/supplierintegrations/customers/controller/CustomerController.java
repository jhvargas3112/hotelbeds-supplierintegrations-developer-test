package com.hotelbeds.supplierintegrations.customers.controller;

import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.customers.services.CustomerService;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping("/{username}")
  public ResponseEntity<CustomerDTO> getCustomerByUserName(@PathVariable String username) {
    try {
      CustomerDTO customerDTO = customerService.getCustomerByUserName(username)
          .orElseThrow(NoSuchElementException::new);

      return ResponseEntity.ok(customerDTO);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<CustomerDTO>> listAllCustomers() {
    return ResponseEntity.ok(customerService.listAllCustomers());
  }


  @PostMapping
  public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO customerDTO) {
    customerDTO = customerService.saveCustomer(customerDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userName}")
        .buildAndExpand(customerDTO.getUserName()).toUri();

    return ResponseEntity.created(location).build();
  }

}
