package in.ravikalla.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@TestConfiguration
@EnableMongoRepositories(basePackages = "in.ravikalla.repositories")
public class TestMongoConfiguration {
    // Spring Boot will auto-configure embedded MongoDB when de.flapdoodle.embed.mongo is on classpath
    // No additional configuration needed for basic testing
}