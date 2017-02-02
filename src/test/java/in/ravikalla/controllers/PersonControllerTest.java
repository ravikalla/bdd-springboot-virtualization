package in.ravikalla.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.reflect.TypeToken;

import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.config.TestConfiguration;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.service.PersonService;
import in.ravikalla.utils.TestUtils;

/**
 * 
 * @author ravi kalla
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@WebAppConfiguration
public class PersonControllerTest {

	private static Log l = LogFactory.getLog(PersonControllerTest.class);

	private MockMvc mockMvc;

	@Autowired
	private PersonService personService;

	@Mock
	private PersonRepository personRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private final String URL_PERSONS = "/persons";
	private final String URL_PERSON = "/person?id=test";

	@Mock
	private static Person objPerson5;
	@Mock
	private static Person objPerson6;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		objPerson5 = stubPersons(5);
		objPerson6 = stubPersons(6);

		when(personService.findById(any(String.class))).thenReturn(objPerson5);
		when(personService.findAll()).thenCallRealMethod();
		when(personRepository.findAll()).thenReturn(Arrays.asList(objPerson5));
	}

	@Test
	public void testFindAll() throws Exception {
		l.info("Start : PersonControllerTest.testFindAll()");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_PERSONS)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(objPerson6))).andReturn();

		Assert.assertEquals("Got success response while getting all persons", 200, result.getResponse().getStatus());

		l.info("All Users : Content : " + result.getResponse().getContentAsString());

		// Verify that the method is called atleast once
		verify(personRepository).findAll();

		Type listType = new TypeToken<ArrayList<Person>>(){}.getType();

		List<Person> lstPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(), listType);
		l.info("Returned list size : " + lstPersons.size());
	}

	@Test
	public void testFindById() throws Exception {
		l.info("Start : PersonControllerTest.testFindById()");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_PERSON)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(objPerson6))).andReturn();

		Assert.assertEquals("Got success response while getting one", 200, result.getResponse().getStatus());

		l.info("One User : Content : " + result.getResponse().getContentAsString());

		// Verify that the method is called atleast once
		verify(personRepository).findById(any(String.class));

		Person objPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Person.class);
		l.info("Returned list size : " + objPersons.getFirstName());
	}

	private Person stubPersons(int intCtr) {
		List<Company> lstCompanies = Arrays.asList(new Company("Infy" + intCtr, "Mysore" + intCtr));
		int[] arrLocationCoordinates = {1, 2};
		Person objPerson = new Person("Ravi" + intCtr, "Kalla" + intCtr, "IT" + intCtr, arrLocationCoordinates, lstCompanies);
		return objPerson;
	}
}
