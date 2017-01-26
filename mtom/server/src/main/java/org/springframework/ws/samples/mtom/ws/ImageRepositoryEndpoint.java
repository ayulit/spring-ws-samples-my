/*
 * Copyright 2005-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.samples.mtom.ws;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.springframework.util.Assert;
import org.springframework.ws.samples.mtom.schema.Image;
import org.springframework.ws.samples.mtom.schema.ObjectFactory;
import org.springframework.ws.samples.mtom.service.ImageRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/** @author Arjen Poutsma */
@Endpoint
public class ImageRepositoryEndpoint {

    private ImageRepository imageRepository;

    private ObjectFactory objectFactory; // xlitand: needed for marshaling ?!

    // xlitand: HashMap for caching
    public static Map<String, SoftReference<java.awt.Image>> imagesCache = new HashMap<String, SoftReference<java.awt.Image>>();
    
    // xlitand: this constructor called from somewhere with 'imageRepository'
    public ImageRepositoryEndpoint(ImageRepository imageRepository) {
        Assert.notNull(imageRepository, "'imageRepository' must not be null");
        
        // xlitand: class parameters initialization
        this.imageRepository = imageRepository;
        this.objectFactory = new ObjectFactory();
    }
    
    // xlitand: this is Store Image Request from the client, I suppose
    // xlitand: request comes as XML and it contains an image
    // xlitand: annotations for marshaling ?!
    @PayloadRoot(localPart = "StoreImageRequest", namespace = "http://www.springframework.org/spring-ws/samples/mtom")
    @ResponsePayload
    public void store(@RequestPayload JAXBElement<Image> requestElement) throws IOException, JAXBException {
        Image request = requestElement.getValue(); // xlitand: getting image from XML ?!
        
        // xlitand: storing image in repository
        imageRepository.storeImage(request.getName(), request.getImage());
    }

    
	// xlitand: method for object creation (from XML?!)
    @PayloadRoot(localPart = "LoadImageRequest", namespace = "http://www.springframework.org/spring-ws/samples/mtom")
    @ResponsePayload
    public JAXBElement<Image> load(@RequestPayload JAXBElement<String> requestElement) throws IOException, JAXBException {
        String name = requestElement.getValue();
        Image response = new Image();
        response.setName(name);
        response.setImage(imageRepository.readImage(name));
        return objectFactory.createLoadImageResponse(response);
    }


}
