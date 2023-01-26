package com.hotelbeds.supplierintegrations.customers.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hotelbeds.supplierintegrations.customers.controller.CustomerController;
import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;
import com.hotelbeds.supplierintegrations.customers.services.CustomerAccessService;
import com.hotelbeds.supplierintegrations.customers.services.CustomerService;
import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetection;
import com.hotelbeds.supplierintegrations.hackertest.detector.LoginAttempNotifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerAccessControllerIntegrationTest {
  @MockBean
  private CustomerService customerService;

  @MockBean
  private LoginAttempNotifier loginAttempNotifier;

  @MockBean
  private HackerDetection hackerDetection;

  @MockBean
  private CustomerAccessService customerAccessService;

  @Autowired
  private MockMvc mvc;

  @Test
  public void when_given_login_call_with_correct_credentials_then_return_not_found_signin_failed()
      throws Exception {
    String body = "{\n"
        + "  \"userName\": \"irene\",\n"
        + "  \"password\": \"123456fds789\"\n"
        + "}";

    when(customerAccessService.login(new CustomerCredentials("irene", "123456789"))).thenReturn(
        false);

    mvc.perform(post("/login/").accept((MediaType.APPLICATION_JSON)).content(body)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

}
