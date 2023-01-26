package com.hotelbeds.supplierintegrations.customers.services;

import com.hotelbeds.supplierintegrations.customers.model.CustomerCredentials;

public interface CustomerAccessService {

  boolean login(CustomerCredentials customerCredentials);

}
