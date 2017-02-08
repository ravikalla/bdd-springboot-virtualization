package in.ravikalla;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.Before;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class Stepdefs {
	@Autowired private WebApplicationContext context;

	@Before
	public void setup() throws IOException {
		MockMvcBuilders.webAppContextSetup(context).build();
	}
}
