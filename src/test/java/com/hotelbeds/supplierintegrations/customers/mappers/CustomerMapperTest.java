package com.hotelbeds.supplierintegrations.customers.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest {

  private CustomerMapperImpl customerMapper;

  @BeforeEach
  public void setUpBefore() {
    customerMapper = new CustomerMapperImpl();

  }

  @Test
  public void when_given_full_filled_customer_dto_then_return_full_filled_customer_entity() {
    CustomerDTO customerDTO = new CustomerDTO("jhonny", "80.238.9.160", "123456");

    Customer customer = customerMapper.to(customerDTO);

    assertNotNull(customer);
    assertEquals("jhonny", customer.getUserName());
    assertEquals("80.238.9.160", customer.getIp());
    assertEquals("123456", customer.getPassword());
    assertNull(customer.getId());

  }

  @Test
  public void when_given_full_filled_customer_entity_then_return_full_filled_customer_dto() {
    Customer customer = new Customer(1L, "jhonny", "80.238.9.160", "123456");

    CustomerDTO customerDTO = customerMapper.to(customer);

    assertNotNull(customerDTO);
    assertEquals("jhonny", customerDTO.getUserName());
    assertEquals("80.238.9.160", customerDTO.getIp());
    assertEquals("123456", customerDTO.getPassword());
  }

  @Test
  public void when_given_null_customer_dto_then_return_null_customer_entity() {
    CustomerDTO customerDTO = null;

    Customer customer = customerMapper.to(customerDTO);

    assertNull(customer);
  }

  @Test
  public void when_given_null_customer_entity_then_return_null_customer_dto() {
    Customer customer = null;

    CustomerDTO customerDTO = customerMapper.to(customer);

    assertNull(customerDTO);
  }

}
