package com.ksicode.service.impl;

import com.ksicode.dto.CustomerRegistrationRequest;
import com.ksicode.dto.CustomerResponse;
import com.ksicode.entity.Customer;
import com.ksicode.exception.DuplicateResourceException;
import com.ksicode.exception.ResourceNotFoundException;
import com.ksicode.service.CustomerDaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDaoService customerDaoService;
    private CustomerServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        underTest = new CustomerServiceImpl(customerDaoService);
    }

    @Test
    void saveCustomer() {
        //Given
        CustomerRegistrationRequest request = CustomerRegistrationRequest.builder()
                .name("Chilufya Owens")
                .email("natasham@ksicode.com")
                .age(26)
                .build();

        when(customerDaoService.existsCustomerWithEmail(request.email()))
                .thenReturn(false);

        //WHen
        underTest.saveCustomer(request);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );
        verify(customerDaoService).createCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowExceptionWhenEmailAlreadyExists() {
        //Given
        CustomerRegistrationRequest request = CustomerRegistrationRequest.builder()
                .name("Chilufya Owens")
                .email("natasham@ksicode.com")
                .age(26)
                .build();

        when(customerDaoService.existsCustomerWithEmail(request.email()))
                .thenReturn(true);

        //WHen
        assertThatThrownBy(()->underTest.saveCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken.");


        //Then
        verify(customerDaoService, never()).createCustomer(any());
    }

    @Test
    void selectAllCustomers() {
        //Given

        //WHen
        underTest.selectAllCustomers();

        //Then
        verify(customerDaoService).getAllCustomers();
    }

    @Test
    void canGetCustomerById() {
        //Given
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("Chilufya Owens")
                .email("natasham@ksicode.com")
                .age(26)
                .build();

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .age(customer.getAge())
                .build();

        when(customerDaoService.getCustomerById(customerId))
                .thenReturn(Optional.of(customer));


        //WHen
        CustomerResponse actual = underTest.selectCustomerById(customerId);

        //Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(customerResponse);

    }

    @Test
    void willThrowExceptionWhenGetCustomerByIdReturnsEmpty(){
        //Given
        Long customerId = 1L;
        when(customerDaoService.getCustomerById(customerId))
                .thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(()-> underTest.selectCustomerById(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found.".formatted(customerId));
    }

    @Test
    void updateCustomerDetails() {
        //Given

        //WHen

        //Then
    }

    @Test
    void deleteCustomer() {
        //Given
        Long customerId = 10L;

        when(customerDaoService.existingCustomerWithId(customerId))
                .thenReturn(true);

        //WHen
        underTest.deleteCustomer(customerId);

        //Then
        verify(customerDaoService).deleteCustomerById(customerId);
    }

    @Test
    void willThrowExceptionWhenDeleteCustomerByIdNotExist() {
        //Given
        Long customerId = 10L;
        when(customerDaoService.existingCustomerWithId(customerId))
                .thenReturn(false);

        //WHen
        assertThatThrownBy(()->underTest.deleteCustomer(customerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found.".formatted(customerId));

        //Then
        verify(customerDaoService, never()).deleteCustomerById(customerId);

    }
}