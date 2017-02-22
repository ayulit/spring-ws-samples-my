package org.springframework.ws.samples.mtom.listeners;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.embed.service.PostgresEmbeddedService;

public class AppListener implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(AppListener.class);
	
	private PostgresEmbeddedService postgres;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		int port = 5429;
        String dbName = "repo";
        String db_user = "postgres";
        String db_host = "localhost";
        String db_password = "123";        
              
        try {
			postgres = new PostgresEmbeddedService(db_host, port, db_user, db_password,  dbName);						
		} catch (IOException e) {			
			e.printStackTrace();
		}

        postgres.start();

        logger.info("EmbeddedPostgreSQL started.");       

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {		
		logger.info("Stopping EmbeddedPostgreSQL... ");		
		postgres.stop();
	}

}
