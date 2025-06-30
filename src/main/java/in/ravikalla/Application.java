package in.ravikalla;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Modern Spring Boot 3.x Application
 * 
 * @author ravi kalla
 */
@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				logger.info("ServletContext initialized: {} - {} - {}", 
					sce.getServletContext().getContextPath(), 
					sce.getServletContext().getServerInfo(), 
					sce.getServletContext().getVirtualServerName());
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				logger.info("ServletContext destroyed: {} - {} - {}", 
					sce.getServletContext().getContextPath(), 
					sce.getServletContext().getServerInfo(), 
					sce.getServletContext().getVirtualServerName());
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
