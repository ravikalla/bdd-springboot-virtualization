package in.ravikalla;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author ravi kalla
 *
 */

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application {
	private static Log l = LogFactory.getLog(Application.class);

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				l.info("Application.ServletContextListener.contextInitialized(...) : ServletContext initialized : " + sce.getServletContext().getContextPath() + " : " + sce.getServletContext().getServerInfo() + " : " + sce.getServletContext().getVirtualServerName());
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				l.info("Application.ServletContextListener.contextDestroyed(...) : ServletContext destroyed : " + sce.getServletContext().getContextPath() + " : " + sce.getServletContext().getServerInfo() + " : " + sce.getServletContext().getVirtualServerName());
			}

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
