package com.hotelbeds.supplierintegrations.customers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  @NonNull
  private String userName;

  @NonNull
  private String ip;

  @NonNull
  private String password;

}
