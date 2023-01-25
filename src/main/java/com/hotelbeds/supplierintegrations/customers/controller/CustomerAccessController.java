package com.hotelbeds.supplierintegrations.customers.controller;

import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;
import com.hotelbeds.supplierintegrations.customers.services.CustomerAccessService;
import com.hotelbeds.supplierintegrations.hackertest.detector.enums.LoginAttempResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class CustomerAccessController {
    private final CustomerAccessService customerAccessService;

    @PostMapping
    public ResponseEntity<LoginAttempResult> login(@RequestBody CustomerCredentials customerCredentials) {
        return customerAccessService.login(customerCredentials) ?
                ResponseEntity.ok(LoginAttempResult.SIGNIN_SUCCESS) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoginAttempResult.SIGNIN_FAILURE);
    }
}
