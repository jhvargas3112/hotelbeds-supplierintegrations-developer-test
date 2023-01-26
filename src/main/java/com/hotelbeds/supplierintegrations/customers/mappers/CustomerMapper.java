package com.hotelbeds.supplierintegrations.customers.mappers;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  Customer to(CustomerDTO customerDTO);

  CustomerDTO to(Customer customer);

  List<CustomerDTO> getCustomersDtos(List<Customer> customers);

}
