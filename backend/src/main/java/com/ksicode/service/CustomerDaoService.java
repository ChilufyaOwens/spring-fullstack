package com.ksicode.service;

import com.ksicode.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDaoService {

    Customer createCustomer(Customer request);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long customerId);
    Customer updateCustomer(Customer updateRequest);
    void deleteCustomerById(Long customerId);
    Boolean existsCustomerWithEmail(String email);
    Boolean existingCustomerWithId(Long customerId);
}
