package com.hotelbeds.supplierintegrations.customers.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @NonNull
  private Long id;

  @NonNull
  @Column(unique = true)
  private String userName;

  @NonNull
  @Column(unique = true)
  private String ip;

  @NonNull
  private String password;

}
