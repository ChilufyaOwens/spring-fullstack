package com.ksicode.service.impl;

import com.ksicode.AbstractTestContainers;
import com.ksicode.entity.Customer;
import com.ksicode.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCServiceTest extends AbstractTestContainers {
    private CustomerJDBCService underTest;
    private final CustomerMapper customerMapper = new CustomerMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCService(
                getJdbcTemplate(),
                customerMapper
        );
    }

    @Test
    void createCustomer() {
        //Given

        //WHen

        //Then
    }

    @Test
    void getAllCustomers() {
        //Given
        Customer customer = Customer.builder()
                .name("Kenzie Chilufya")
                .email("chilufyakenzie@ksicode.com")
                .age(23)
                .build();
        underTest.createCustomer(customer);

        //WHen
        List<Customer> actual = underTest.getAllCustomers();

        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void getCustomerById() {
        //Given
        String customerEmail = "chilufya@ksicode.com";
        Customer customer = Customer.builder()
                .name("Kenzie Chilufya")
                .email(customerEmail)
                .age(23)
                .build();
        underTest.createCustomer(customer);

        Long customerId = underTest.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customerEmail))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //WHen
        var actual = underTest.getCustomerById(customerId);
        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(customerId);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptySelectCustomerById() {
        //Given
        Long customerId = -1L;

        //WHen
        var actual = underTest.getCustomerById(customerId);
        //Then
        assertThat(actual).isEmpty();

    }

    @Test
    void updateCustomer() {
        //Given

        //WHen

        //Then
    }

    @Test
    void existsCustomerWithEmail() {
        //Given

        //WHen

        //Then
    }

    @Test
    void deleteCustomerById() {
        //Given

        //WHen

        //Then
    }
}