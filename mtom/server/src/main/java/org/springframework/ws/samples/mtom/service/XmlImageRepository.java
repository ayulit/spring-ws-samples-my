package org.springframework.ws.samples.mtom.service;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.samples.mtom.config.ImageXML;

public class XmlImageRepository implements ImageRepository {

	private static final Logger LOG = Logger.getLogger(XmlImageRepository.class);
		
	@Autowired
	private JAXBContext jc;
	
	@Override
	public Image readImage(String name) throws IOException, JAXBException {
				
		LOG.info("Loading from XML image " + name);
		
		Unmarshaller um = jc.createUnmarshaller();
		
		File file = getFile(name);	
        if(!file.exists()){
        	LOG.error("File " + name + " not found.");
            return null;
        }
        
        ImageXML imageXml = (ImageXML) um.unmarshal(file);
		
		return imageXml.getImage();
	}

	@Override
	public void storeImage(String name, Image image) throws IOException, JAXBException {

		LOG.info("Saving as XML image " + name + " [" + image.getWidth(null) + "x" + image.getHeight(null) + "]");
		
		Marshaller m = jc.createMarshaller();		
		
		m.setProperty(m.JAXB_FORMATTED_OUTPUT, true); 
				
		ImageXML imageXml = new ImageXML(name, image);
				
		File file = getFile(name);
				
		m.marshal(imageXml, file);
				
		LOG.info("Marshalled to file " + file.getAbsolutePath());
	
	}

    private File getFile(String name) {
    	
    	File upThree = new File(System.getProperty("catalina.home")).getParentFile().getParentFile().getParentFile();    	
    	String path = upThree.getAbsolutePath();
    	String fileName = path + File.separator  + name + ".xml";
    	    	
        File file = new File(fileName);

        return file;
    }

}
