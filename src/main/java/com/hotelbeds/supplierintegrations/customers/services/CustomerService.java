package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<CustomerDTO> getCustomerByUserName(String userName);

    List<CustomerDTO> listAllCustomers();

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

}
