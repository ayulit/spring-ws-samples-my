package org.springframework.ws.samples.mtom.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("org.springframework.ws.samples.mtom.db") // searching @Component classes, particularly here @Service("jpaFruitServce")
@EnableTransactionManagement                             // Enables @Transactional
public class TransactionManagersConfig {

	@Autowired
	EntityManagerFactory emf;
	
	@Autowired
	private DataSource dataSource;

	/** EntityManagerFactory needs TransactionManager for transactional access to data.
        Luckily Spring has TransactionManager straight for JPA!  */
	@Bean
	public JpaTransactionManager transactionManager() {
		
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(emf);		
				
		return transactionManager;		
	}
	
}
