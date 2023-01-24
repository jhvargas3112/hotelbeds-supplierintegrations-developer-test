package com.hotelbeds.supplierintegrations.customers.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CustomerDTO {

  @NonNull
  @Id
  private String userName;

  @NonNull
  private String password;

}
