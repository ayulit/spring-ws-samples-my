package org.springframework.ws.samples.mtom.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:psql.properties")
public class DataSourcesConfig {

	@Autowired
	private Environment environment;
	
	/* xlitand: dataSource is like generalised factory for connections
	 * We will use Spring's realization of 'dataSource'*/
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(environment.getRequiredProperty("psql.driverClassName"));
        ds.setUrl(environment.getRequiredProperty("psql.url"));
        ds.setUsername(environment.getRequiredProperty("psql.username"));
        ds.setPassword(environment.getRequiredProperty("psql.password"));
        		
        return ds;		
	}

}
