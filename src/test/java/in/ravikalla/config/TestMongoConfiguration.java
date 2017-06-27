package in.ravikalla.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

@EnableMongoRepositories
@Configuration
public class TestMongoConfiguration extends AbstractMongoConfiguration {
//	Start : Update for commenting Spring REST service
//	@Autowired
//	public WebApplicationContext webApplicationContext;
//
//	@Bean
//	MockMvc mockMvc() {
//		return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//
//	@Bean
//	public PersonRepository personRepository() {
//		return Mockito.mock(PersonRepository.class);
//	}
//	End : Update for commenting Spring REST service

	@Override
	protected String getDatabaseName() {
		return "ravi_db1";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new Fongo("mongo-test").getMongo();
	}
}
