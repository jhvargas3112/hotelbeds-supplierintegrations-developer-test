package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;
import com.hotelbeds.supplierintegrations.customers.model.CustomerDTO;
import com.hotelbeds.supplierintegrations.hackertest.detector.LoginAttempNotifier;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerAccessServiceImpl implements CustomerAccessService {

    private final CustomerService customerService;

    private final LoginAttempNotifier loginAttempNotifier;

    @Override
    public boolean login(CustomerCredentials customerCredentials) {
        Optional<CustomerDTO> optCustomerDTO = customerService.getCustomerByUserName(customerCredentials.getUserName());

        if (optCustomerDTO.isPresent()) {
            CustomerDTO customerDTO = optCustomerDTO.get();
            if (StringUtils.equals(customerCredentials.getPassword(), customerDTO.getPassword())) {
                loginAttempNotifier.notifyLoginAttempResult(customerDTO.getUserName(), customerDTO.getIp(), true);
                return true;
            } else {
                loginAttempNotifier.notifyLoginAttempResult(customerDTO.getUserName(), customerDTO.getIp(), false);
                return false;
            }
        } else {
            return false;
        }
    }

}
