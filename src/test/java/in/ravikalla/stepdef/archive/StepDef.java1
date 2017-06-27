package in.ravikalla.stepdef;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.reflect.TypeToken;

import cucumber.api.java.Before;
import cucumber.api.java8.En;
import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.utils.TestUtils;

@TestPropertySource("/application.yml")
public class StepDef implements En {

	private static Log l = LogFactory.getLog(StepDef.class);

	private static String PERSON_ID;

	@Autowired
	private MockMvc mockMvc;

	public PersonRepository personRepository;

	private final String URL_SAVE_PERSON = "/saveperson";
	private final String URL_GET_PERSONS = "/persons";
	private final String URL_GET_PERSON = "/person?id=";

	private static Person objPerson5;
	private static Person objPerson6;

	@Before
	public void setup() throws IOException {
		l.info("Start : PersonControllerTest.setUp()");

		personRepository = org.mockito.Mockito.mock(PersonRepository.class);

		objPerson5 = stubPersons(5);
		objPerson6 = stubPersons(6);

		l.info("End : PersonControllerTest.setUp()");
	}

	public StepDef() {
		Given("^Step starting desc \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$",
				(String strScenarioNumber, String strFirstName, String strlastName) -> {
					l.info("Start : PersonControllerTest.testPersonSave() : " + strScenarioNumber + " : " + strFirstName
							+ " : " + strlastName);

					String strPerson5JSON = TestUtils.objectToJson(objPerson5);
					MvcResult result;
					try {
						result = mockMvc
								.perform(MockMvcRequestBuilders.post(URL_SAVE_PERSON)
										.contentType(MediaType.APPLICATION_JSON_UTF8).content(strPerson5JSON)
										.accept(MediaType.APPLICATION_JSON_UTF8))// .andExpect(status().isOk())
								.andReturn();

						Assert.assertEquals("Inserted record", 201, result.getResponse().getStatus());

						l.info("Save person response content : " + result.getResponse().getContentAsString());

						// Verify that the method is called atleast once
						// verify(personRepository).save(any(Person.class));

						Collection<String> headerNames = result.getResponse().getHeaderNames();
						headerNames.forEach(strHeaderName -> {
							l.debug("140 : Header name in the response after saving : " + strHeaderName);
						});
						Person objPerson = TestUtils.jsonToObject(result.getResponse().getContentAsString(),
								Person.class);

						l.info("End : PersonControllerTest.testPersonSave() : "
								+ ((null == objPerson) ? "Object not present" : objPerson.getFirstName()));
					} catch (Exception e) {
						l.info("102 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
				});

		Given("^Step one desc \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$",
				(String strScenarioNumber, String strFirstName, String strlastName) -> {
					System.out.println("Start : PersonControllerTest.testPersonFindAll() : " + strScenarioNumber + " : "
							+ strFirstName + " : " + strlastName);
					l.info("Start : PersonControllerTest.testPersonFindAll() : " + strScenarioNumber + " : "
							+ strFirstName + " : " + strlastName);
					try {
						MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_GET_PERSONS)
								.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
								.content(TestUtils.objectToJson(objPerson5))).andReturn();

						Assert.assertEquals("Got success response while getting all persons", 200,
								result.getResponse().getStatus());

						l.info("All Users : Content : " + result.getResponse().getContentAsString());

						// Verify that the method is called atleast once
						// verify(personRepository).findAll();

						Type listType = new TypeToken<ArrayList<Person>>() {
						}.getType();

						List<Person> lstPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(),
								listType);
						l.info("End : PersonControllerTest.testPersonFindAll() : " + lstPersons.size() + " : "
								+ lstPersons.get(0).getFirstName() + " : " + (PERSON_ID = lstPersons.get(0).getId()));
					} catch (Exception e) {
						l.info("102 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
				});

		Given("^Step two desc \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$",
				(String strScenarioNumber, String strFirstName, String strlastName) -> {
					l.info("Start : PersonControllerTest.testPersonFindById() : " + strScenarioNumber + " : "
							+ strFirstName + " : " + strlastName);
					try {
						MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_GET_PERSON + PERSON_ID)
								.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
								.content(TestUtils.objectToJson(objPerson6)))// .andExpect(status().isOk())
								.andReturn();

						Assert.assertEquals("Got success response while getting one", 200,
								result.getResponse().getStatus());

						l.info("One User : Content : " + result.getResponse().getContentAsString());

						// Verify that the method is called atleast once
						// verify(personRepository).findById(any(String.class));

						Person objPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(),
								Person.class);

						l.info("End : PersonControllerTest.testPersonFindById() : " + objPersons.getFirstName());
					} catch (Exception e) {
						l.info("134 : Exception e : " + e);
						Assert.fail("Exception e : " + e);
					}
				});
	}

	public static Person stubPersons(int intCtr) {
		List<Company> lstCompanies = Arrays.asList(new Company("Infy" + intCtr, "Mysore" + intCtr));
		int[] arrLocationCoordinates = { 1, 2 };
		Person objPerson = new Person("Ravi" + intCtr, "Kalla" + intCtr, "IT" + intCtr, arrLocationCoordinates,
				lstCompanies);
		return objPerson;
	}
}
