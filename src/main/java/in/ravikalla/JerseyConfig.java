package in.ravikalla;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		packages("in.ravikalla.controllers");
	}
}
