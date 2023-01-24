package com.hotelbeds.supplierintegrations.customers.repositories;

import com.hotelbeds.supplierintegrations.customers.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
