package com.ksicode.repository;

import com.ksicode.AbstractTestContainers;
import com.ksicode.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainers {

    private final CustomerRepository underTest;
    @Autowired
    CustomerRepositoryTest(CustomerRepository underTest) {
        this.underTest = underTest;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsCustomerById() {
        //Given
        Customer customer = Customer.builder()
                .name("Kenzie Owens")
                .email("chilufya@ksicode.com")
                .age(23)
                .build();
        underTest.save(customer);

        Long customerId = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //WHen
        var actual = underTest.existsCustomerById(customerId);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existCustomerByIdFailsWhenIdIsPresent() {
        //Given
        Long customerId = -1L;
        //WHen
        var actual = underTest.existsCustomerById(customerId);
        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerByEmail() {
        //Given
        Customer customer = Customer.builder()
                .name("Natasha Owens")
                .email("natasha@ksicode.com")
                .age(23)
                .build();
        underTest.save(customer);
        //WHen
        var actual = underTest.existsCustomerByEmail(customer.getEmail());

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailNotPresent() {
        //Given
        String customerEmail = "kenzieo@ksicode.com";

        //WHen
        var actual = underTest.existsCustomerByEmail(customerEmail);

        //Then
        assertThat(actual).isFalse();
    }


}