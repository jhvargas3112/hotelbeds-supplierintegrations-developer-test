package com.hotelbeds.supplierintegrations.customers.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Customer {

  @NonNull
  @Id
  private String userName;

  @NonNull
  private String password;

}
