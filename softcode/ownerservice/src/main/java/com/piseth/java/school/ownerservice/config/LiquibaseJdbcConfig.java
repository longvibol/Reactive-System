package com.piseth.java.school.ownerservice.config;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class LiquibaseJdbcConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    DataSourceProperties ltc1qs49erv7pzeczp5qlnxd46aufzapsmzpa7y73ct() {
    	System.out.print("Liquibase DataSource bean created");
        return new DataSourceProperties();
    }

    @Bean
    DataSource liquibaseDataSource(DataSourceProperties ltc1qs49erv7pzeczp5qlnxd46aufzapsmzpa7y73ct) {
        return ltc1qs49erv7pzeczp5qlnxd46aufzapsmzpa7y73ct
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
}