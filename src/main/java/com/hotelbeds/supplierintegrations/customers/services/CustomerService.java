package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  List<CustomerDTO> listAllCustomers(Pageable pageable);

  CustomerDTO saveCustomer(CustomerDTO customerDTO);

}
