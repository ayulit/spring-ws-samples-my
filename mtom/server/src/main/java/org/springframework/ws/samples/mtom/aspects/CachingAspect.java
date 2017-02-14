package org.springframework.ws.samples.mtom.aspects;

import java.awt.Image;
import java.lang.ref.SoftReference;

import org.apache.log4j.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.Assert;
import org.springframework.ws.samples.mtom.ws.ImageRepositoryEndpoint;

@Aspect
public class CachingAspect {
	
	private static final Logger LOG = Logger.getLogger(CachingAspect.class);

	private void validateName(String name){
		
		LOG.info("AROUND: validation " + name);
		
		Assert.isTrue((name.length() <= 30 && name !=null ), "'filename' must not be longer than 30 symbols or empty");
	}
	
	@Pointcut ("execution(* *.storeImage(..)) && args(name, image)")
	public void storeImageMethod(String name, Image image) {}
	
	@Pointcut ("execution(* *.readImage(..)) && args(name)")
	public void readImageMethod(String name) {}

	@Around("storeImageMethod(name, image)")
	public void aroundStoreImage(ProceedingJoinPoint jp, String name, Image image) throws Throwable {		
		
		LOG.info("AROUND : " + jp.getSignature().getName());
		
		validateName(name);
		
		// xlitand: creating reference to 'image' object for caching it
		SoftReference<Image> imageRef = new SoftReference<Image>(image);		
		ImageRepositoryEndpoint.imagesCache.put(name, imageRef);
		
		LOG.info("AROUND: Image stored in cache");
				
		jp.proceed(); // for bean's method run, so after caching we save image to repo
	}
	
	@Around("readImageMethod(name)")
	public Object aroundReadImage(ProceedingJoinPoint jp, String name) throws Throwable {
		
		LOG.info("AROUND : " + jp.getSignature().getName());
	
		validateName(name);
		
		Image cashedImage = ImageRepositoryEndpoint.imagesCache.get(name).get();
		
		if(cashedImage != null){
			
			LOG.info("AROUND: returning from cache Image " + name);
			
			// xlitand: temporary disabling read from cache to test read from DB
			//return cashedImage;
		}
		
		return jp.proceed(); // for bean's method run  
	}
	
}
