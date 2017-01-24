package org.springframework.ws.samples.mtom.aspects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectsConfig {

	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
	
}
