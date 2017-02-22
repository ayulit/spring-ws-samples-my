package org.springframework.ws.samples.mtom.db;

public interface ImageService {

	// xlitand: Save image-ROW to table
	void save(ImageORM image);
	
	// xlitand: Returns image-ROW with 'name' from table
	ImageORM findByName (String name);
}
