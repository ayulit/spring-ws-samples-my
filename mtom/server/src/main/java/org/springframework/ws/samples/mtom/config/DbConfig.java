package org.springframework.ws.samples.mtom.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/** xlitand: JDBC connection config */
@Configuration
@PropertySource("classpath:psql.properties")
public class DbConfig {
	
	@Autowired
	private Environment environment;
	
	/* xlitand: dataSource is like generalised factory for connections
	 * We will use Spring's realization of 'dataSource'*/
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(environment.getRequiredProperty("psql.url"));
        ds.setUsername(environment.getRequiredProperty("psql.username"));
        ds.setPassword(environment.getRequiredProperty("psql.password"));
        
        return ds;		
	}
	
	@Autowired
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
