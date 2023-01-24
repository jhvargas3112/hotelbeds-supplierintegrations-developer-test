package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import com.hotelbeds.supplierintegrations.customers.mappers.CustomerMapper;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.customers.repositories.CustomerRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerMapper customerMapper;

  private final CustomerRepository customerRepository;


  @Override
  public List<CustomerDTO> listAllCustomers(Pageable pageable) {
    Page<Customer> customersEntitiesPage = customerRepository.findAll(pageable);
    List<Customer> customerEntities = customersEntitiesPage.getContent();

    return customerMapper.getCustomersDtos(customerEntities);
  }

  @Override
  public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    Customer customer = customerMapper.to(customerDTO);

    customerRepository.save(customer);

    return customerDTO;
  }

}
