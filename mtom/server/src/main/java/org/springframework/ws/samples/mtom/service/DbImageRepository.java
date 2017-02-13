package org.springframework.ws.samples.mtom.service;

import java.awt.Image;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbImageRepository implements ImageRepository {

	private static final Logger LOG = Logger.getLogger(DbImageRepository.class);
	
	// xlitand: to persist using JDBC TODO delete later 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
