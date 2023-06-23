package com.ksicode.service.impl;

import com.ksicode.entity.Customer;
import com.ksicode.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerDaoServiceImplTest {
    private CustomerDaoServiceImpl underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerDaoServiceImpl(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createCustomer() {
        //Given
        Customer customer = Customer.builder()
                .id(1L)
                .name("Kenzie Chilufya")
                .email("kenziec@ksicode.zm")
                .age(24)
                .build();
        //WHen
        underTest.createCustomer(customer);

        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void getAllCustomers() {
        //WHen
        underTest.getAllCustomers();

        //Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void getCustomerById() {
        //Given
        Long customerId = -1L;

        //WHen
        underTest.getCustomerById(customerId);
        //Then
        verify(customerRepository).findById(customerId);
    }

    @Test
    void updateCustomer() {
        //Given
        Customer customer = Customer.builder()
                .id(1L)
                .name("Chilufya Chilufya")
                .email("chilufyac@ksicode.com")
                .age(25)
                .build();

        //WHen
        underTest.updateCustomer(customer);

        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsCustomerWithEmail() {
        //Given
        String email = "chilufyak@ksicode.com";
        //WHen
        underTest.existsCustomerWithEmail(email);
        //Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerWithId() {
        //Given
        Long customerId  = 1L;
        //WHen
        underTest.existingCustomerWithId(customerId);
        //Then
        verify(customerRepository).existsCustomerById(customerId);
    }

    @Test
    void deleteCustomerById() {
        //Given
        Long customerId = 1L;
        //WHen
        underTest.deleteCustomerById(customerId);
        //Then
        verify(customerRepository).deleteById(customerId);
    }
}