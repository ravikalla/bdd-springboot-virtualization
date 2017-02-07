package in.ravikalla.config;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import in.ravikalla.repositories.PersonRepository;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages={    
    "in.ravikalla.controllers", "in.ravikalla.service"})
public class TestConfiguration {
	@Autowired
	public WebApplicationContext webApplicationContext;

	@Bean
	public PersonRepository personRepository() {
		return Mockito.mock(PersonRepository.class);
	}

	@Bean
	MockMvc mockMvc() {
	    return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
}
