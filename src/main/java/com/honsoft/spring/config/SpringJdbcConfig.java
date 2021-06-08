package com.honsoft.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.honsoft.spring.StudentJDBCTemplate;

@Configuration
@ComponentScan("com.honsoft.spring")
public class SpringJdbcConfig {
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/quickguide");
        dataSource.setUsername("shoppingnt");
        dataSource.setPassword("Shoppingnt2021!@");

        return dataSource;
    }
    
    @Bean 
    public StudentJDBCTemplate studentJDBCTemplate() {
    	StudentJDBCTemplate studentJDBCTemplate = new StudentJDBCTemplate();
    	studentJDBCTemplate.setDataSource(mysqlDataSource());
    	return studentJDBCTemplate;
    }
}