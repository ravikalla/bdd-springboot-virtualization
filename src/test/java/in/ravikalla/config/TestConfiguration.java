package in.ravikalla.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import in.ravikalla.repositories.PersonRepository;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages={    
    "in.ravikalla.controllers", "in.ravikalla.service"})
public class TestConfiguration {
	@Bean
	public PersonRepository personRepository() {
		return Mockito.mock(PersonRepository.class);
	}
}
