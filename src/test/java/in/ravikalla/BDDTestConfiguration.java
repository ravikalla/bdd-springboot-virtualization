package in.ravikalla;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import in.ravikalla.config.TestConfiguration;

/**
 * @author ravi kalla
 * @since 4-Feb-2017
 *
 */

@RunWith(Cucumber.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestConfiguration.class })

@CucumberOptions (features = "classpath:features"
//, glue={"in.ravikalla.stepdef"}
, plugin = {"pretty", "html:target/test-report"})

public class BDDTestConfiguration {}
