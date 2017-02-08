package in.ravikalla.config;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

import in.ravikalla.repositories.PersonRepository;

@EnableWebMvc
@EnableMongoRepositories
@Configuration
@ComponentScan(basePackages = { "in.ravikalla.controllers", "in.ravikalla.service" })
public class TestConfiguration extends AbstractMongoConfiguration {
	@Autowired
	public WebApplicationContext webApplicationContext;

	@Bean
	MockMvc mockMvc() {
		return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Bean
	public PersonRepository personRepository() {
		return Mockito.mock(PersonRepository.class);
	}

	@Override
	protected String getDatabaseName() {
		return "ravi_db";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new Fongo("mongo-test").getMongo();
	}
}
