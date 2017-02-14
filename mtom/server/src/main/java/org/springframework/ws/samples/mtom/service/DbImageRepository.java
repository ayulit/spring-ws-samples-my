package org.springframework.ws.samples.mtom.service;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

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
		                        "name VARCHAR(50) NOT NULL," + 
								"img BYTEA" + ")");
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

		LOG.info("Reading file " + name + " from DB");		
		
		// xlitand: here we will use lambda as RowMapper
		byte[] imageInByte = jdbcTemplate.queryForObject("SELECT img from t_images WHERE name = ?",
				new Object[]{name}, (rs, rowNum) -> rs.getBytes("img"));
						
		// convert byte array back to Image
		InputStream in = new ByteArrayInputStream(imageInByte);
		Image imageFromConvert = ImageIO.read(in);
		
		return imageFromConvert;
	}

	@Override
	public void storeImage(String name, Image image) throws IOException, JAXBException {
		
		/* xlitand: convert Image to byte array */
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		ImageIO.write((RenderedImage) image, 
						StringUtils.getFilenameExtension(name), baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		
		/* Saving to DB */		
        jdbcTemplate.update("INSERT INTO t_images (name,img) VALUES (?,?)",
        		name, imageInByte);
        
        baos.close();
        
        LOG.info("Stored to DB image " + name + " [" + image.getWidth(null) + "x" + image.getHeight(null) + "]");

	}

}
