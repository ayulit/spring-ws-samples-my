package org.springframework.ws.samples.mtom.service;

import java.awt.Image;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbImageRepository implements ImageRepository {

	private static final Logger LOG = Logger.getLogger(DbImageRepository.class);
	
	// xlitand: TODO: delete when Hibernate
	private static final String SQL_ERROR_STATE_SCHEMA_EXISTS = "42P04";
	private static final String SQL_ERROR_STATE_TABLE_EXISTS = "42P07";
	
	
	// xlitand: to persist using JDBC TODO delete later 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// xlitand: temp init of creation DB and table TODO: delete when Hibernate
	@PostConstruct
	public void init() {	
		createTableIfNotExists();
	}

    /** xlitand: Creating table */
	private void createTableIfNotExists() {

		try {
			jdbcTemplate.update("CREATE TABLE t_images (" + 
		                        "name VARCHAR(50) NOT NULL PRIMARY KEY" + ")");			
			LOG.info("Created table t_images");
		} catch (DataAccessException e) {
			
			Throwable causeException = e.getCause();
			
			if (causeException instanceof PSQLException) {
				
				PSQLException psqlException = (PSQLException) causeException;
				
				if (psqlException.getSQLState().equals(SQL_ERROR_STATE_TABLE_EXISTS)) {					
					LOG.info("Table already exists.");
				} else {
					throw e;
				}				
			} else {
				throw e;
			}
		}
		
	}
	
	@Override
	public Image readImage(String name) throws IOException, JAXBException {

		LOG.error("Reading file " + name + " from DB is under construction...");		
		return null;
	}

	@Override
	public void storeImage(String name, Image image) throws IOException, JAXBException {
				
		// xlitand: TODO implement inserting image!		
        jdbcTemplate.update("INSERT INTO t_images (name) VALUES (?)",
        		name);
        
        LOG.info("Stored to DB image " + name + " [" + image.getWidth(null) + "x" + image.getHeight(null) + "]");

	}

}
