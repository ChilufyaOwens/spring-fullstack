package com.ksicode.service.impl;

import com.ksicode.dto.ApiResponse;
import com.ksicode.dto.CustomerRegistrationRequest;
import com.ksicode.dto.CustomerResponse;
import com.ksicode.entity.Customer;
import com.ksicode.exception.DuplicateResourceException;
import com.ksicode.exception.ResourceNotFoundException;
import com.ksicode.service.CustomerDaoService;
import com.ksicode.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDaoService customerDaoService;

    public CustomerServiceImpl(@Qualifier("jpa-service") CustomerDaoService customerDaoService) {
        this.customerDaoService = customerDaoService;
    }

    @Override
    public ApiResponse saveCustomer(CustomerRegistrationRequest request) {
        //Check if customer already exists with email
        if(Boolean.TRUE.equals(customerDaoService.existsCustomerWithEmail(request.email()))){
            //Throw an exception
            throw new DuplicateResourceException("Email already taken.");
        }
        Customer customer = Customer.builder()
                .name(request.name())
                .age(request.age())
                .email(request.email())
                .build();
        Customer savedCustomer = customerDaoService.createCustomer(customer);
        return buildApiResponse("Customer created successfully.", savedCustomer);
    }

    @Override
    public List<CustomerResponse> selectAllCustomers() {
        List<Customer> customers =customerDaoService.getAllCustomers();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        customers.forEach(customer -> customerResponseList.add(buildCustomerResponse(customer)));
        return customerResponseList;
    }

    @Override
    public CustomerResponse selectCustomerById(Long customerId) {
        Optional<Customer> customerById = customerDaoService.getCustomerById(customerId);
        Customer customer = customerById.orElseThrow(() ->
                new ResourceNotFoundException("Customer with id [%s] not found.".formatted(customerId))
        );
        return buildCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerDetails(Long customerId, CustomerRegistrationRequest request) {
        Optional<Customer> optionalCustomer = customerDaoService.getCustomerById(customerId);
        Customer existingCustomer = optionalCustomer.orElseThrow(() ->
                new ResourceNotFoundException("Customer with id [%s] not found.".formatted(customerId)));

        //Check if update email is no taken
        if(Boolean.TRUE.equals(customerDaoService.existsCustomerWithEmail(request.email()))){
            throw new DuplicateResourceException("Update email is already taken.");
        }
        existingCustomer.setName(request.name());
        existingCustomer.setEmail(request.email());
        existingCustomer.setAge(request.age());

        Customer updatedCustomer = customerDaoService.updateCustomer(existingCustomer);
        return buildCustomerResponse(updatedCustomer);
    }

    @Override
    public ApiResponse deleteCustomer(Long customerId) {
        Boolean doesCustomerExistWithId = customerDaoService.existingCustomerWithId(customerId);
        if(Boolean.FALSE.equals(doesCustomerExistWithId)){
            throw new ResourceNotFoundException("Customer with id [%s] not found.".formatted(customerId));
        }
        customerDaoService.deleteCustomerById(customerId);
        return buildApiResponse("Customer with id [%s] successfully deleted.".formatted(customerId), null);
    }


    private ApiResponse buildApiResponse(String message, Object data){
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * This method builds Customer response object
     * @param customer customer
     * @return customer response
     */
    private CustomerResponse buildCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .age(customer.getAge())
                .build();
    }
}
