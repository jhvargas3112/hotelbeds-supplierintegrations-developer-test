package com.hotelbeds.supplierintegrations.customers.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

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
    @Column(unique=true)
    private String userName;

    @NonNull
    @Column(unique=true)
    private String ip;

    @NonNull
    private String password;

}
