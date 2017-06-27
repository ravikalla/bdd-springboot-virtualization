package in.ravikalla.stepdef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import in.ravikalla.Application;
import in.ravikalla.bean.Person;
import in.ravikalla.bean.PersonForTest;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.util.BDDUtil;
import in.ravikalla.utils.APICalls;

@ContextConfiguration(classes = {
			Application.class
		}
		, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestPropertySource("/application.yml")
public class PersonRecordStepsTest_Jersey implements En {

	private Logger l = Logger.getLogger(this.getClass());

	@Autowired
	public PersonRepository personRepository;
	private Response response;
	private String strActualJSONContent;
	private String strActualXMLContent;

	@Value("${local.server.port}")
	private int port;

	@Before
	public void setup() throws IOException {
		l.info("Start : PersonRecordStepsTest_Jersey.setUp()");

		MockitoAnnotations.initMocks(this);
		RestAssured.port = port;

		l.info("End : PersonRecordStepsTest_Jersey.setUp()");
	}

	public PersonRecordStepsTest_Jersey() {
		Given("^Database has no persons for jersey flow$",
				() -> {
					l.debug("Start : PersonRecordStepsTest_Jersey() : insert records in DB");
					try {
						response = APICalls.getAllPersonsFromDB();

						Assert.assertEquals("Got success response while getting all persons", 200, response.getStatusCode());

						List<Person> lstPersons = BDDUtil.jsonToObject(response.getBody().asString(), new TypeToken<ArrayList<Person>>() {}.getType());

						if ((null != lstPersons) && (0 != lstPersons.size()))
							Assert.fail("No persons should be there in DB");
					} catch (Exception e) {
						l.info("92 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
					l.debug("End : PersonRecordStepsTest_Jersey() : insert records in DB");
				});
		And("^User inserted a person information for jersey flow$",
				(DataTable objDataTable) -> {
					l.debug("Start : PersonRecordStepsTest_Jersey() : insert records in db");

					List<PersonForTest> lstPersonForTest = objDataTable.asList(PersonForTest.class);
					lstPersonForTest.forEach(objPersonForTest -> {
						String strPerson5JSON = BDDUtil.objectToJson(objPersonForTest.getPerson());
						try {
							response = APICalls.savePersonToDB(strPerson5JSON);
							Person objPersonInserted = BDDUtil.jsonToObject(response.getBody().asString(), Person.class);

							Assert.assertEquals("Input and returned first name must be same", objPersonForTest.getFirstName(), objPersonInserted.getFirstName());
							Assert.assertEquals("Input and returned last name must be same", objPersonForTest.getLastName(), objPersonInserted.getLastName());

//							Mockito.verify(personRepository).save(Mockito.any(Person.class));
						} catch (Exception e) {
							l.info("114 : Exception e : " + e);
							Assert.fail("Exception e : " + e);
						}
					});
					l.debug("End : PersonRecordStepsTest_Jersey() : insert records in db");
				});
		Then("^Check if there are \"([^\"]*)\" users in the DB for jersey flow$",
				(String strUserCount) -> {
					l.info("Start : PersonRecordStepsTest_Jersey() : check user count in DB for : " + strUserCount);
					try {
						response = APICalls.getAllPersonsFromDB();
						List<Person> lstPersons = BDDUtil.jsonToObject(response.getBody().asString(), new TypeToken<ArrayList<Person>>() {}.getType());

						Assert.assertEquals("Expected user count and actual user count from DB should be same", Integer.parseInt(strUserCount), lstPersons.size());
					} catch (Exception e) {
						l.info("130 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
					l.info("End : PersonRecordStepsTest_Jersey() : check user count in DB for : " + strUserCount);
				});

		Given("^Delete all persons from DB in Jersey flow$",
				() -> {
					l.info("Start : PersonRecordStepsTest_Jersey() : delete all persons");
					try {
						response = APICalls.deleteAllPersonsFromDB();
					} catch (Exception e) {
						l.info("142 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
					l.info("End : PersonRecordStepsTest_Jersey() : delete all persons");
				});
	}
}
