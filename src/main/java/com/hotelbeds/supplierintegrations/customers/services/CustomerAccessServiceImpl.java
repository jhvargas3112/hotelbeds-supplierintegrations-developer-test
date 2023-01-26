package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetection;
import com.hotelbeds.supplierintegrations.hackertest.detector.LoginAttempNotifier;
import com.hotelbeds.supplierintegrations.hackertest.detector.LoginAttempNotifierImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerAccessServiceImpl implements CustomerAccessService {

    private static final Logger log = LoggerFactory.getLogger(LoginAttempNotifierImpl.class);

    private final CustomerService customerService;

    private final LoginAttempNotifier loginAttempNotifier;

    private final HackerDetection hackerDetection;


    @Override
    public boolean login(CustomerCredentials customerCredentials) {
        Optional<CustomerDTO> optCustomerDTO = customerService.getCustomerByUserName(customerCredentials.getUserName());

        if (optCustomerDTO.isPresent()) {
            CustomerDTO customerDTO = optCustomerDTO.get();
            if (StringUtils.equals(customerCredentials.getPassword(), customerDTO.getPassword())) {
                detectPossibleMaliciousActivity(loginAttempNotifier.notifyLoginAttempResult(customerDTO.getUserName(), customerDTO.getIp(), true));
                return true;
            } else {
                detectPossibleMaliciousActivity(loginAttempNotifier.notifyLoginAttempResult(customerDTO.getUserName(), customerDTO.getIp(), false));
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
