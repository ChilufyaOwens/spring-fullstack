package com.ksicode;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class AbstractTestContainers {
    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgresqlContainer.getJdbcUrl(),
                postgresqlContainer.getUsername(),
                postgresqlContainer.getPassword()
        ).load();
        flyway.migrate();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgresqlContainer
            = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ksicode-dao-unit-test")
            .withUsername("ksicode")
            .withPassword("ksicode");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry propertyRegistry){
        propertyRegistry.add("spring.datasource.url",
                postgresqlContainer::getJdbcUrl
        );

        propertyRegistry.add("spring.datasource.username",
                postgresqlContainer::getUsername
        );
        propertyRegistry.add("spring.datasource.password",
                postgresqlContainer::getPassword
        );
    }

    private static DataSource getDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgresqlContainer.getDriverClassName())
                .url(postgresqlContainer.getJdbcUrl())
                .username(postgresqlContainer.getUsername())
                .password(postgresqlContainer.getPassword())
                .build();
    }

    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

}
