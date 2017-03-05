package org.springframework.ws.samples.mtom.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class ImageServiceImpl implements ImageService {

	private static final Logger LOG = Logger.getLogger(ImageServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ImageORM findByName(String name) {

		TypedQuery<ImageORM> query = em.createQuery("select i from ImageORM i where i.name=:filename", ImageORM.class);
		query.setParameter("filename", name);
		
		ImageORM  imageRow = query.getSingleResult();
		
		return imageRow;
	}
		
	@Override
	public void save(ImageORM image) {

		em.merge(image);			
		
		LOG.info("Inserting new image: " + image.getName());

	}

}
