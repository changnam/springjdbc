package com.honsoft.spring.config;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.honsoft.spring.StudentDAO;
import com.honsoft.spring.StudentDAOImpl;
import com.zaxxer.hikari.HikariDataSource;

import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

@Configuration
@ComponentScan("com.honsoft.spring")
@PropertySource("classpath:/app.properties")
public class SpringJdbcConfig {
	@Autowired
	Environment env;

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
	public DataSourceInitializer dataSourceInitializer(@Qualifier("mysqlProxyDataSource") DataSource datasource) {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		resourceDatabasePopulator.addScript(new ClassPathResource("mybatis/mysql/schema-mysql.sql"));
		resourceDatabasePopulator.addScript(new ClassPathResource("mybatis/mysql/data-mysql.sql"));
		// resourceDatabasePopulator.addScript(new
		// ClassPathResource("ddl/common/user-data.sql"));
		// resourceDatabasePopulator.addScript(new
		// ClassPathResource("ddl/common/order-data.sql"));

		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(datasource);
		dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
		dataSourceInitializer.setEnabled(env.getProperty("mysql.datasource.initialize", Boolean.class, false));

		return dataSourceInitializer;
	}

	@Bean
	public StudentDAO studentDAOImpl() {
		StudentDAOImpl studentJDBCTemplate = new StudentDAOImpl();
		studentJDBCTemplate.setDataSource(mysqlProxyDataSource());
		return studentJDBCTemplate;
	}

	@Bean
	public ProxyDataSource mysqlProxyDataSource() {
		ProxyDataSource dataSource = ProxyDataSourceBuilder.create(mysqlDataSource()) // pass original datasource
				.logQueryBySlf4j() // logQueryBySlf4j(), logQueryByJUL(), logQueryToSysOut()
				.countQuery() // enable query count metrics
				.logSlowQueryBySlf4j(10, TimeUnit.MINUTES) // also by sl4j, jul, system out
				.proxyResultSet() // enable proxying ResultSet
				// .listener(myListener) // register my custom listener
				.build();

		return dataSource;
	}
}