package in.ravikalla.stepdef;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.reflect.TypeToken;

import cucumber.api.java.Before;
import cucumber.api.java8.En;
import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.utils.TestUtils;

//@ContextConfiguration(classes=ShibaApplication.class, loader=SpringApplicationContextLoader.class)
//@IntegrationTest
//@WebAppConfiguration

@TestPropertySource("/application.yml")
public class StepDef implements En {

	private static Log l = LogFactory.getLog(StepDef.class);

	private final String PERSON_ID = "test";

	private MockMvc mockMvc;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private final String URL_GET_PERSONS = "/persons";
	private final String URL_GET_PERSON = "/person?id=" + PERSON_ID;
	private final String URL_SAVE_PERSON = "/saveperson";

	private static Person objPerson5;

	private static Person objPerson6;

	@Before
	public void setUp() {
		l.info("Start : PersonControllerTest.setUp()");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		objPerson5 = stubPersons(5);
		objPerson6 = stubPersons(6);

		when(personRepository.findAll()).thenReturn(Arrays.asList(objPerson5));
		when(personRepository.findById(any(String.class))).thenReturn(objPerson6);
		// when(personRepository.save(any(Person.class))).then

		l.info("End : PersonControllerTest.setUp()");
	}

	public StepDef() {
		Given("^Step one desc \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$", (String strScenarioNumber, String strFirstName, String strlastName) -> {
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
					verify(personRepository).findAll();

					Type listType = new TypeToken<ArrayList<Person>>() {
					}.getType();

					List<Person> lstPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(),
							listType);
					l.info("End : PersonControllerTest.testPersonFindAll() : " + lstPersons.size() + " : "
							+ lstPersons.get(0).getFirstName());
				} catch (Exception e) {
					l.info("102 : Exception e : " + e);
					Assert.fail("Exception e : " + e);
				}
			});

		Given("^Step two desc \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$", (String strScenarioNumber, String strFirstName, String strlastName) -> {
				l.info("Start : PersonControllerTest.testPersonFindById() : " + strScenarioNumber + " : "
						+ strFirstName + " : " + strlastName);
				try {
					MvcResult result = mockMvc
							.perform(MockMvcRequestBuilders.post(URL_GET_PERSON)
									.contentType(MediaType.APPLICATION_JSON_UTF8)
									.accept(MediaType.APPLICATION_JSON_UTF8)
									.content(TestUtils.objectToJson(objPerson6)))// .andExpect(status().isOk())
							.andReturn();

					Assert.assertEquals("Got success response while getting one", 200,
							result.getResponse().getStatus());

					l.info("One User : Content : " + result.getResponse().getContentAsString());

					// Verify that the method is called atleast once
					verify(personRepository).findById(any(String.class));

					Person objPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(),
							Person.class);

					l.info("End : PersonControllerTest.testPersonFindById() : " + objPersons.getFirstName());
				} catch (Exception e) {
					l.info("134 : Exception e : " + e);
					Assert.fail("Exception e : " + e);
				}
			});

		Given("^Step3 desc \"^(.*)$\", \"^(.*)$\", \"^(.*)$\"$",
				(String strScenarioNumber, String strFirstName, String strlastName) -> {
					l.info("Start : PersonControllerTest.testPersonSave() : " + strScenarioNumber + " : "
							+ strFirstName + " : " + strlastName);
					try {
						String strPerson5JSON = TestUtils.objectToJson(objPerson5);
						MvcResult result = mockMvc
								.perform(MockMvcRequestBuilders.post(URL_SAVE_PERSON).contentType(MediaType.APPLICATION_JSON_UTF8)
										.content(strPerson5JSON).accept(MediaType.APPLICATION_JSON_UTF8))// .andExpect(status().isOk())
								.andReturn();

						Assert.assertEquals("Got success response while getting one", 200, result.getResponse().getStatus());

						l.info("One User : Content : " + result.getResponse().getContentAsString());

						// Verify that the method is called atleast once
						verify(personRepository).save(any(Person.class));

						Collection<String> headerNames = result.getResponse().getHeaderNames();
						headerNames.forEach(strHeaderName -> {
							System.out.println("140 : Header name in the response after saving : " + strHeaderName);
						});
						Person objPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Person.class);

						l.info("End : PersonControllerTest.testPersonSave() : " + objPersons.getFirstName());
					} catch (Exception e) {
						l.info("136 : Exception e : " + e);
					}
				});
		// Given("^Step1 test$", (String arg0) -> {
		// l.info("arg0 : " + arg0);
		// });
		// And("^he has a receipt$", () -> {
		// // do nothing ...
		// });
		// When("^he returns the microwave$", () -> {
		// // do nothing ...
		// });
		// Then("^Jeff should be refunded (\\d+)$", (Integer arg0) -> {
		// Assert.assertEquals(price, arg0);
		// });
	}

	private Person stubPersons(int intCtr) {
		List<Company> lstCompanies = Arrays.asList(new Company("Infy" + intCtr, "Mysore" + intCtr));
		int[] arrLocationCoordinates = { 1, 2 };
		Person objPerson = new Person("Ravi" + intCtr, "Kalla" + intCtr, "IT" + intCtr, arrLocationCoordinates,
				lstCompanies);
		return objPerson;
	}
}
