/**
 * Project: Employee Payroll Management System (OOPs Using Java - Case Study 3)
 * Section: 802A
 * Group Members:
 *   1. Kartik Rana   (UID: 25BCS11387)
 *   2. Akshay Boora  (UID: 25BCS11028)
 *   3. Kul Mahajan   (UID: 25BCS12318)
 */
package com.payroll.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    public static void verifyJdbcConnection(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("[JDBC Connection Verified] Connected to "
                        + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion()
                        + " | Database: " + conn.getCatalog());
            }
        } catch (SQLException e) {
            System.err.println("[JDBC Error] Failed to establish connection: " + e.getMessage());
        }
    }
}
