package org.springframework.ws.samples.mtom.db;

public interface ImageService {

	
	void save(ImageORM image);
	
	
	ImageORM findByName (String name);
}
