package org.springframework.ws.samples.mtom.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class EntityManagerFactoriesConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		
		Properties jpaProperties = getJpaProperties();
		
		LocalContainerEntityManagerFactoryBean emf =  new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan(new String[] { "org.springframework.ws.samples.mtom.db" });
		emf.setJpaProperties(jpaProperties);
		
		return emf;		
	}

	
	private Properties getJpaProperties() {
		
		Properties properties = new Properties();
		
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.max_fetch_depth", "3");
		properties.put("hibernate.jdbc.fetch_size", "50");
		properties.put("hibernate.jdbc.batch_size", "10");
		properties.put("hibernate.show_sql", "true");	
		properties.put("hibernate.hbm2ddl.auto", "create");
		
		return properties;
	}
	
}
