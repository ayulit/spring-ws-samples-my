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

	// xlitand: The most important bean of all this JPA config!
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		
		Properties jpaProperties = getJpaProperties();
		
		LocalContainerEntityManagerFactoryBean emf =  new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);                              // injecting bean of a DataSource
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());   // Using Hibernate as ORM tool (persistence provider)
		emf.setPackagesToScan(new String[] { "org.springframework.ws.samples.mtom.db" });  // Needed for find classes annotated by ORM annotations. We don't need to use persisitence.xml then (works from Spring 3.1)
		emf.setJpaProperties(jpaProperties);
		
		return emf;		
	}

	/** This gives properties for current persistence provider (Hibernate) */
	private Properties getJpaProperties() {
		
		Properties properties = new Properties();
		
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.max_fetch_depth", "3");
		properties.put("hibernate.jdbc.fetch_size", "50");
		properties.put("hibernate.jdbc.batch_size", "10");
		properties.put("hibernate.show_sql", "true");
		
		// xlitand: 
		// Drop and re-create the database schema on startup,
		// -create: every time
		// -update: if ONLY model changed!
		properties.put("hibernate.hbm2ddl.auto", "create");
		
		return properties;
	}
	
}
