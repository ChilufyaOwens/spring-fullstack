package com.ksicode.controller;

import com.ksicode.dto.ApiResponse;
import com.ksicode.dto.CustomerRegistrationRequest;
import com.ksicode.dto.CustomerResponse;
import com.ksicode.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    /**
     * This API creates a new customer
     * @param request create customer request
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createCustomer(@Valid @RequestBody CustomerRegistrationRequest request){
        ApiResponse createdCustomer = customerService.saveCustomer(request);
        return new ResponseEntity<>(createdCustomer,HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable(name = "id") Long customerId) {
        CustomerResponse customerResponse = customerService.selectCustomerById(customerId);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customerResponseList = customerService.selectAllCustomers();
        return new ResponseEntity<>(customerResponseList, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable(name = "id")Long customerId,
                                                      @Valid @RequestBody CustomerRegistrationRequest updateRequest){
        CustomerResponse updatedCustomer = customerService.updateCustomerDetails(customerId, updateRequest);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ApiResponse> deleteCustomerById(@PathVariable(name = "id") Long customerId){
        ApiResponse deletedCustomerById = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(deletedCustomerById, HttpStatus.OK);
    }
}
