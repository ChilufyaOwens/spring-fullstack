package com.ksicode.journey;

import com.ksicode.dto.CustomerRegistrationRequest;
import com.ksicode.dto.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerIntegrationTest {
    private final WebTestClient webTestClient;
    private static final String CUSTOMER_URI = "api/v1/customers";

    @Autowired
    public CustomerIntegrationTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void canRegisterACustomer() {
        //Create a customer registration request
        CustomerRegistrationRequest request = CustomerRegistrationRequest.builder()
                .name("Mongu Owens")
                .email("mongu@ksicode.com")
                .age(37)
                .build();

        //Send a post request
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerIntegrationTest.class)
                .exchange()
                .expectStatus()
                .isCreated();

        //Get all customers
        List<CustomerResponse> allCustomers = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();

        //Make sure a customer is present
        assertThat(allCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(CustomerResponse.builder()
                        .name(request.name())
                        .email(request.email())
                        .age(request.age())
                        .build());

        //Get customer by customerId
        var customerId = allCustomers.stream()
                .filter(c -> c.getEmail().equals(request.email()))
                .map(CustomerResponse::getId)
                .findFirst()
                .orElseThrow();

        CustomerResponse expectedCustomer = CustomerResponse.builder()
                .id(customerId)
                .name(request.name())
                .email(request.email())
                .age(request.age())
                .build();

        CustomerResponse actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .pathSegment(String.valueOf(customerId))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteACustomer() {
        //Create a customer registration request
        CustomerRegistrationRequest request = CustomerRegistrationRequest.builder()
                .name("Mumbwa Owens")
                .email("mumbwa@ksicode.com")
                .age(17)
                .build();

        //Send a post request
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerIntegrationTest.class)
                .exchange()
                .expectStatus()
                .isCreated();

        //Get all customers
        List<CustomerResponse> allCustomers = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();


        //Get customer by customerId
        var customerId = allCustomers.stream()
                .filter(c -> c.getEmail().equals(request.email()))
                .map(CustomerResponse::getId)
                .findFirst()
                .orElseThrow();

        //Delete customer by ID
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .pathSegment(String.valueOf(customerId))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        //Get Customer by ID
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .pathSegment(String.valueOf(customerId))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

    }

    @Test
    void canUpdateCustomerDetails() {
        //Create a customer registration request
        CustomerRegistrationRequest request = CustomerRegistrationRequest.builder()
                .name("Chilanga Owens")
                .email("chilanga@ksicode.com")
                .age(16)
                .build();

        //Send a post request
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerIntegrationTest.class)
                .exchange()
                .expectStatus()
                .isCreated();

        //Get all customers
        List<CustomerResponse> allCustomers = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();


        //Get customerId
        var customerId = allCustomers.stream()
                .filter(c -> c.getEmail().equals(request.email()))
                .map(CustomerResponse::getId)
                .findFirst()
                .orElseThrow();

        //Update customer details
        CustomerRegistrationRequest updateRequest =CustomerRegistrationRequest
                .builder()
                .name("Mumbai Owens")
                .email("mumbaio@ksicode.com")
                .age(20)
                .build();

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .pathSegment(String.valueOf(customerId))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //Get Customer by ID
        CustomerResponse updatedCustomer = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CUSTOMER_URI)
                        .pathSegment(String.valueOf(customerId))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerResponse>() {
                })
                .returnResult()
                .getResponseBody();

        CustomerResponse expectedCustomer = CustomerResponse.builder()
                .id(customerId)
                .name(updateRequest.name())
                .email(updateRequest.email())
                .age(updateRequest.age())
                .build();

        assertThat(updatedCustomer).usingRecursiveComparison().isEqualTo(expectedCustomer);

    }
}
