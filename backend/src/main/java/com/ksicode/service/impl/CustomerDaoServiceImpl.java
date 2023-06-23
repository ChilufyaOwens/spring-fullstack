package com.ksicode.service.impl;

import com.ksicode.entity.Customer;
import com.ksicode.repository.CustomerRepository;
import com.ksicode.service.CustomerDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "jpa-service")
@RequiredArgsConstructor
public class CustomerDaoServiceImpl implements CustomerDaoService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer request) {
        return customerRepository.save(request);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);

    }

    @Override
    public Customer updateCustomer(Customer updateRequest) {
        return customerRepository.save(updateRequest);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public Boolean existsCustomerWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public Boolean existingCustomerWithId(Long customerId) {
        return customerRepository.existsCustomerById(customerId);
    }

}
