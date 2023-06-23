package com.ksicode.repository;

import com.ksicode.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsCustomerById(Long customerId);
    Boolean existsCustomerByEmail(String email);
}
