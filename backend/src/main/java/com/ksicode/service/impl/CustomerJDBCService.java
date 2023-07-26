package com.ksicode.service.impl;

import com.ksicode.dto.ApiResponse;
import com.ksicode.entity.Customer;
import com.ksicode.mapper.CustomerMapper;
import com.ksicode.service.CustomerDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "jdbc-service")
@RequiredArgsConstructor
public class CustomerJDBCService implements CustomerDaoService {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerMapper customerMapper;

    @Override
    public Customer createCustomer(Customer request) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES(?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql,
                request.getName(),
                request.getEmail(),
                request.getAge());

        if(result == 0){
            return null;
        }
        return request;
    }

    @Override
    public List<Customer> getAllCustomers() {
        var sql = """
                SELECT c.id, c.name, c.email, c.age
                FROM customer c
                """;
        return jdbcTemplate.query(sql, customerMapper);
    }

    /**
     * This method build api response api
     * @param isSuccess
     * @param message
     * @param data
     * @return
     */
    private ApiResponse buildApiResponse(Boolean isSuccess, String message, Object data) {
        return ApiResponse.builder()
                .success(isSuccess)
                .message(message)
                .data(data)
                .build();
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        var sql = """
                SELECT c.id, c.name, c.email, c.age
                FROM customer c
                WHERE c.id = ?
                """;
        Optional<Customer> customerResponse = jdbcTemplate.query(sql, customerMapper, customerId)
                .stream()
                .findFirst();

        return customerResponse;


    }

    @Override
    public Customer updateCustomer(Customer updateRequest) {
        return null;
    }

    @Override
    public Boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT COUNT(c.id)
                FROM customer c
                WHERE c.email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Boolean existingCustomerWithId(Long customerId) {
        var sql = """
                SELECT COUNT(c.id)
                FROM Customer c
                WHERE c.id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customerId);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        var sql = """
                DELETE 
                FROM customer c
                WHERE c.id = ?
                """;
        int result = jdbcTemplate.update(sql, customerId);
    }
}
