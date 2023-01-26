package com.hotelbeds.supplierintegrations.customers.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetection;
import com.hotelbeds.supplierintegrations.hackertest.detector.LoginAttempNotifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerAccessServiceImplTest {

  private static final String USER_NAME = "pepe";

  private static final String NOTIFICATION = "80.238.9.170,1674673432577,SIGNIN_SUCCESS,pepe";

  private CustomerAccessServiceImpl customerAccessService;

  @Mock
  private CustomerService customerService;

  @Mock
  private LoginAttempNotifier loginAttempNotifier;

  @Mock
  private HackerDetection hackerDetection;

  private static List<CustomerDTO> customersDtos;

  @BeforeAll
  static void setup() {
    customersDtos = new ArrayList<>();
    customersDtos.add(new CustomerDTO("ana", "80.238.9.175", "123"));
    customersDtos.add(new CustomerDTO("pepe", "80.238.9.170", "12345"));
    customersDtos.add(new CustomerDTO("luis", "80.238.9.180", "1234567"));
  }

  @BeforeEach
  public void setUpBefore() {
    customerAccessService = new CustomerAccessServiceImpl(customerService, loginAttempNotifier,
        hackerDetection);

    when(customerService.getCustomerByUserName(USER_NAME)).thenReturn(
        Optional.ofNullable(customersDtos.get(1)));
    when(hackerDetection.parseLine(NOTIFICATION)).thenReturn(null);

  }

  @Test
  public void when_login_attemp_with_correct_credentials_the_return_true() {
    CustomerCredentials credentials = new CustomerCredentials("pepe", "12345");

    when(loginAttempNotifier.notifyLoginAttempResult(customersDtos.get(1).getUserName(),
        customersDtos.get(1).getIp(), true)).thenReturn(NOTIFICATION);

    boolean resp = customerAccessService.login(credentials);

    verify(customerService, atLeast(1)).getCustomerByUserName(USER_NAME);
    verify(loginAttempNotifier, atLeast(1)).notifyLoginAttempResult(
        customersDtos.get(1).getUserName(), customersDtos.get(1).getIp(), true);
    verify(hackerDetection, atLeast(1)).parseLine(NOTIFICATION);

    assertTrue(resp);
  }

  @Test
  public void when_login_attemp_with_bad_credentials_the_return_false() {
    CustomerCredentials credentials = new CustomerCredentials("pepe", "12345fcsdafsdf");

    when(loginAttempNotifier.notifyLoginAttempResult(customersDtos.get(1).getUserName(),
        customersDtos.get(1).getIp(), false)).thenReturn(NOTIFICATION);

    boolean resp = customerAccessService.login(credentials);

    verify(customerService, atLeast(1)).getCustomerByUserName(USER_NAME);
    verify(loginAttempNotifier, atLeast(1)).notifyLoginAttempResult(
        customersDtos.get(1).getUserName(), customersDtos.get(1).getIp(), false);
    verify(hackerDetection, atLeast(1)).parseLine(NOTIFICATION);

    assertFalse(resp);
  }

}
