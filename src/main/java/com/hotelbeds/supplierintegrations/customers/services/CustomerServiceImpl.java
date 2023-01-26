package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import com.hotelbeds.supplierintegrations.customers.mappers.CustomerMapper;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.customers.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerByUserName(String userName) {
        Optional<Customer> customer = customerRepository.findByUserName(userName);

        return customer.map(c -> customerMapper.to(c));
    }

    @Override
    public List<CustomerDTO> listAllCustomers() {

        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customersDtos = customerMapper.getCustomersDtos(customers);

        return customersDtos;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.to(customerDTO);

        customerRepository.save(customer);

        return customerDTO;
    }

}
