package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetection;
import com.hotelbeds.supplierintegrations.hackertest.detector.LoginAttempNotifier;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerAccessServiceImpl implements CustomerAccessService {

  private final CustomerService customerService;

  private final LoginAttempNotifier loginAttempNotifier;

  private final HackerDetection hackerDetection;


  @Override
  public boolean login(CustomerCredentials customerCredentials) {
    Optional<CustomerDTO> optCustomerDTO = customerService.getCustomerByUserName(
        customerCredentials.getUserName());

    if (optCustomerDTO.isPresent()) {
      CustomerDTO customerDTO = optCustomerDTO.get();
      if (StringUtils.equals(customerCredentials.getPassword(), customerDTO.getPassword())) {
        detectPossibleMaliciousActivity(
            loginAttempNotifier.notifyLoginAttempResult(customerDTO.getUserName(),
                customerDTO.getIp(), true));
        return true;
      } else {
        detectPossibleMaliciousActivity(
            loginAttempNotifier.notifyLoginAttempResult(customerDTO.getUserName(),
                customerDTO.getIp(), false));
        return false;
      }
    } else {
      return false;
    }
  }

  private void detectPossibleMaliciousActivity(String notification) {
    String maliciousIP = hackerDetection.parseLine(notification);

    Optional.ofNullable(maliciousIP).ifPresent(ip -> log.info("{}", ip));

  }

}
