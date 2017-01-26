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
	
	// xlitand: @Autowired means auto-accordance with bean
	@Autowired
	private JAXBContext jc;
	
	@Override
	public Image readImage(String name) throws IOException, JAXBException {
				
		LOG.info("Loading from XML image " + name);
		
		/* xlitand: unmarshaling config */
		
		// xlitand: for read
		Unmarshaller um = jc.createUnmarshaller();
		
		// xlitand: file for xml reading
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
		
		/* xlitand: marshaling config */
		
		// xlitand: for write
		Marshaller m = jc.createMarshaller();		
		// xlitand: will be marshalled into formatted XML
		m.setProperty(m.JAXB_FORMATTED_OUTPUT, true); 
		
		/* xlitand: serialized object config */
		ImageXML imageXml = new ImageXML(name, image);
		
		// xlitand: creating file for xml saving
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
