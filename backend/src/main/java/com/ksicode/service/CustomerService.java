package com.ksicode.service;

import com.ksicode.dto.ApiResponse;
import com.ksicode.dto.CustomerRegistrationRequest;
import com.ksicode.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    ApiResponse saveCustomer(CustomerRegistrationRequest request);
    List<CustomerResponse> selectAllCustomers();
    CustomerResponse selectCustomerById(Long customerId);
    CustomerResponse updateCustomerDetails(Long customerId, CustomerRegistrationRequest request);
    ApiResponse deleteCustomer(Long customerId);
}
