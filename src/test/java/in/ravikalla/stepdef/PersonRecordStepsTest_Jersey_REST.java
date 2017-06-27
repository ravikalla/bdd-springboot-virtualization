package in.ravikalla.stepdef;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import in.ravikalla.Application;
import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.bean.PersonForTest;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.service.PersonService;
import in.ravikalla.util.BDDUtil;
import in.ravikalla.utils.APICalls;
import in.ravikalla.utils.TestUtils;

@ContextConfiguration(classes = {
		Application.class
		}
		, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource("/application.yml")
public class PersonRecordStepsTest_Jersey_REST implements En {

	private Logger l = Logger.getLogger(this.getClass());

	@Mock
	public PersonService personService;
	Response response;
	String strActualJSONContent;
	String strActualXMLContent;

	@Value("${local.server.port}")
	int port;

	@Before
	public void setup() throws IOException {
		l.info("Start : PersonRecordStepsTest_Jersey_REST.setUp()");

		MockitoAnnotations.initMocks(this);
		RestAssured.port = port;

		l.info("End : PersonRecordStepsTest_Jersey_REST.setUp()");
	}

	public PersonRecordStepsTest_Jersey_REST() {
		Given("^User inserted a person information in \"www.ravikalla.in\"$",
				(DataTable objDataTable) -> {
					l.debug("Start : PersonRecordStepsTest_Jersey_REST() : insert records in website");

					List<PersonForTest> lstPersonForTest = objDataTable.asList(PersonForTest.class);
					lstPersonForTest.forEach(objPersonForTest -> {
						String strPerson5JSON = BDDUtil.objectToJson(objPersonForTest.getPerson());
						try {
							response = APICalls.savePersonsToREST(strPerson5JSON);

							Person objPersonInserted = BDDUtil.jsonToObject(response.getBody().asString(), Person.class);

							Assert.assertEquals("Input and returned first name must be same", objPersonForTest.getFirstName(), objPersonInserted.getFirstName());
							Assert.assertEquals("Input and returned last name must be same", objPersonForTest.getLastName(), objPersonInserted.getLastName());

						} catch (Exception e) {
							l.info("94 : Exception e : " + e);
							Assert.fail("Exception e : " + e);
						}
					});
					l.debug("End : PersonRecordStepsTest_Jersey_REST() : insert records in website");
				});
		Then("^Check if there are \"([^\"]*)\" users in \"www.ravikalla.in\"$",
				(String strUserCount) -> {
					try {
						l.info("Start : PersonRecordStepsTest_Jersey_REST() : check user count in website for : " + strUserCount);
						response = APICalls.getAllPersonsFromREST();

						List<Person> lstPersons = BDDUtil.jsonToObject(response.getBody().asString(), new TypeToken<ArrayList<Person>>() {}.getType());

						Assert.assertEquals("Expected user count and actual user count should be same", Integer.parseInt(strUserCount), lstPersons.size());
					} catch (Exception e) {
						l.info("102 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
					l.info("End : PersonRecordStepsTest_Jersey_REST() : check user count in website for : " + strUserCount);
				});

		Given("^Delete all persons from \"www.ravikalla.in\"$",
				() -> {
					l.info("Start : PersonRecordStepsTest_Jersey_REST() : delete all persons");
					try {
						response = APICalls.deleteAllPersonsFromREST();
					} catch (Exception e) {
						l.info("102 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
				});
	}
}
