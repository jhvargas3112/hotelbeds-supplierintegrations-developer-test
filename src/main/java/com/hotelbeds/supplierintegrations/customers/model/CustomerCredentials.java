package com.hotelbeds.supplierintegrations.customers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCredentials {

    @NonNull
    private String userName;

    @NonNull
    private String password;

}
