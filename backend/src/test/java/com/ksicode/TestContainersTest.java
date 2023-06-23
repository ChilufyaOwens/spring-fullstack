package com.ksicode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TestContainersTest extends AbstractTestContainers {

    @Test
    void canStartPostgresDB() {
      assertThat(postgresqlContainer.isRunning()).isTrue();
      assertThat(postgresqlContainer.isCreated()).isTrue();
    }


}
