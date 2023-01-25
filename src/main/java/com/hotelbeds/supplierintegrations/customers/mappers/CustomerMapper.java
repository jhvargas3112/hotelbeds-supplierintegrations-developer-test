package com.hotelbeds.supplierintegrations.customers.mappers;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer to(CustomerDTO customerDTO);

    CustomerDTO to(Customer customer);

    List<CustomerDTO> getCustomersDtos(List<Customer> customers);

}
