package org.springframework.ws.samples.mtom.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** xlitand: This class makes operations with DB */
@Service       // =@Component (which means that is bean for <context:component-scan..)
@Repository    // =@Component, just informs that class has data access logics
@Transactional // for defining transaction requirements (see Pro.Spring ch.9)
public class ImageServiceImpl implements ImageService {

	private static final Logger LOG = Logger.getLogger(ImageServiceImpl.class);
	
	@PersistenceContext // for injection: JPA classics
	private EntityManager em;
	
	@Override
	public ImageORM findByName(String name) {
		
		// xlitand:
		// using method of EntityManager with JPQL
		// returns implementation of interface!
		TypedQuery<ImageORM> query = em.createQuery("select i from ImageORM i where i.name=:filename", ImageORM.class);
		query.setParameter("filename", name);
		
		ImageORM  imageRow = query.getSingleResult(); // row extraction as an object
		
		return imageRow;
	}
		
	@Override
	public void save(ImageORM image) {

		em.merge(image);			
		
		LOG.info("Inserting new image: " + image.getName());

	}

}
