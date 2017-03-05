package org.springframework.ws.samples.mtom.config;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.ws.samples.mtom.schema.Image;


@XmlRootElement(name = "Image")
public class ImageXML extends Image {
	
	public ImageXML() {}
	
	public ImageXML(String name, java.awt.Image image) {
		this.name = name;		
		this.image = image;
	}
	
}
