package com.honsoft.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.honsoft.spring.StudentDAO;
import com.honsoft.spring.StudentDAOImpl;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.honsoft.spring")
public class SpringJdbcConfig {
    @Bean
    public HikariDataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/quickguide");
        dataSource.setUsername("shoppingnt");
        dataSource.setPassword("Shoppingnt2021!@");
        return new HikariDataSource(dataSource);
    }
    
    @Bean 
    public StudentDAO studentDAOImpl() {
    	StudentDAOImpl studentJDBCTemplate = new StudentDAOImpl();
    	studentJDBCTemplate.setDataSource(mysqlDataSource());
    	return studentJDBCTemplate;
    }
}