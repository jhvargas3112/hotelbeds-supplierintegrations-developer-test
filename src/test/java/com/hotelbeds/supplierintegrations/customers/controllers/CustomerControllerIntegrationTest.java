package com.hotelbeds.supplierintegrations.customers.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hotelbeds.supplierintegrations.customers.controller.CustomerController;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.customers.services.CustomerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerIntegrationTest {

  @MockBean
  private CustomerService customerService;

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  public void setUpBefore() {
    List<CustomerDTO> customersDtos = new ArrayList<>();
    customersDtos.add(new CustomerDTO("ana", "80.238.9.175", "123"));
    customersDtos.add(new CustomerDTO("pepe", "80.238.9.170", "12345"));
    customersDtos.add(new CustomerDTO("luis", "80.238.9.180", "1234567"));

    CustomerDTO customerDTO = new CustomerDTO("irene", "80.238.9.182", "123456789");

    when(customerService.listAllCustomers()).thenReturn(customersDtos);
    when(customerService.getCustomerByUserName("pepe")).thenReturn(
        Optional.ofNullable(customersDtos.get(1)));
    when(customerService.saveCustomer(customerDTO)).thenReturn(customerDTO);
  }

  @Test
  public void when_given_get_all_customers_call_then_return_json_array_with_customers_entities()
      throws Exception {

    mvc.perform(get("/customers/")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$.[0].userName", is("ana")))
        .andExpect(jsonPath("$.[1].userName", is("pepe")))
        .andExpect(jsonPath("$.[2].userName", is("luis")));

  }

  @Test
  public void when_given_get_customer_by_username_call_then_return_json_object_with_customer_entity()
      throws Exception {

    mvc.perform(get("/customers/pepe/")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.userName", is("pepe")))
        .andExpect(jsonPath("$.ip", is("80.238.9.170")))
        .andExpect(jsonPath("$.password", is("12345")));

  }

  @Test
  public void when_given_get_customer_by_username_call_with_not_exists_username_then_return_not_found_response()
      throws Exception {

    mvc.perform(get("/customers/pepita/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void when_given_create_customer_call_then_return_a_created_customer_dto()
      throws Exception {
    String body = "{\n"
        + "  \"userName\": \"irene\",\n"
        + "  \"ip\": \"80.238.9.182\",\n"
        + "  \"password\": \"123456789\"\n"
        + "}";

    MvcResult result = mvc.perform(
        post("/customers/").accept((MediaType.APPLICATION_JSON)).content(body)
            .contentType(MediaType.APPLICATION_JSON)).andReturn();

    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    assertEquals("http://localhost/customers/irene",
        response.getHeader(HttpHeaders.LOCATION));
  }


}
