package org.springframework.ws.samples.mtom.service;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.ws.samples.mtom.db.ImageORM;
import org.springframework.ws.samples.mtom.db.ImageService;

public class DbImageRepository implements ImageRepository {

	private static final Logger LOG = Logger.getLogger(DbImageRepository.class);
	
	@Autowired
	private ImageService imageService;	
	
	@Override
	public Image readImage(String name) throws IOException, JAXBException {

		LOG.info("Reading image " + name + " from DB");		
				
		ImageORM imageRow = imageService.findByName(name);		
		
		byte[] imageInByte = imageRow.getImg();
		
		InputStream in = new ByteArrayInputStream(imageInByte);
		Image imageFromConvert = ImageIO.read(in);
		
		return imageFromConvert;
	}

	@Override
	public void storeImage(String name, Image image) throws IOException, JAXBException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		ImageIO.write((RenderedImage) image, 
						StringUtils.getFilenameExtension(name), baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		
		ImageORM imageRow = new ImageORM();
		imageRow.setName(name);
		imageRow.setImg(imageInByte);
		
		imageService.save(imageRow);
		
        baos.close();
        
        LOG.info("Stored to DB image " + name + " [" + image.getWidth(null) + "x" + image.getHeight(null) + "]");

	}

}
