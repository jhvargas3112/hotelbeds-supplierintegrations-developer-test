package com.hotelbeds.supplierintegrations.customers.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import com.hotelbeds.supplierintegrations.customers.mappers.CustomerMapper;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.customers.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

  private CustomerServiceImpl customerService;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private CustomerMapper customerMapper;

  private static List<Customer> customers;

  private static List<CustomerDTO> customersDtos;

  @BeforeAll
  static void setup() {
    customersDtos = new ArrayList<>();
    customersDtos.add(new CustomerDTO("ana", "80.238.9.175", "123"));
    customersDtos.add(new CustomerDTO("pepe", "80.238.9.170", "12345"));
    customersDtos.add(new CustomerDTO("luis", "80.238.9.180", "1234567"));

    customers = new ArrayList<>();
    customers.add(new Customer(1L, "ana", "80.238.9.175", "123"));
    customers.add(new Customer(2L, "pepe", "80.238.9.170", "12345"));
    customers.add(new Customer(3L, "luis", "80.238.9.180", "1234567"));
  }

  @BeforeEach
  public void setUpBefore() {
    customerService = new CustomerServiceImpl(customerRepository, customerMapper);
  }

  @Test
  public void test_when_listCustomers_then_returnAllCustomers() {

    final int EXPECTED_SIZE = 3;

    when(customerRepository.findAll()).thenReturn(customers);
    when(customerMapper.getCustomersDtos(customers)).thenReturn(customersDtos);

    List<CustomerDTO> listAllCustomers = customerService.listAllCustomers();

    verify(customerRepository, atLeast(1)).findAll();
    verify(customerMapper, atLeast(1)).getCustomersDtos(customers);

    assertEquals(EXPECTED_SIZE, listAllCustomers.size());

    Optional<CustomerDTO> first = listAllCustomers.stream().findFirst();
    assertTrue(StringUtils.equals(first.get().getUserName(), "ana"));
    assertTrue(StringUtils.equals(first.get().getIp(), "80.238.9.175"));
  }

}
