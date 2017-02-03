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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.reflect.TypeToken;

import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.config.TestConfiguration;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.utils.TestUtils;

/**
 * @author ravi kalla
 * @since 1-Feb-2017
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@WebAppConfiguration
public class PersonControllerTest {

	private static Log l = LogFactory.getLog(PersonControllerTest.class);

	private MockMvc mockMvc;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private final String URL_PERSONS = "/persons";
	private final String URL_PERSON = "/person?id=test";

	
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

		l.info("End : PersonControllerTest.setUp()");
	}

	@Test
	public void testPersonFindAll() throws Exception {
		l.info("Start : PersonControllerTest.testPersonFindAll()");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_PERSONS)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(objPerson6))).andReturn();

		Assert.assertEquals("Got success response while getting all persons", 200, result.getResponse().getStatus());

		l.info("All Users : Content : " + result.getResponse().getContentAsString());

		// Verify that the method is called atleast once
		verify(personRepository).findAll();

		Type listType = new TypeToken<ArrayList<Person>>(){}.getType();

		List<Person> lstPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(), listType);
		l.info("End : PersonControllerTest.testPersonFindAll() : " + lstPersons.size());
	}

	@Test
	public void testPersonFindById() throws Exception {
		l.info("Start : PersonControllerTest.testPersonFindById()");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_PERSON)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(objPerson6))).andReturn();

		Assert.assertEquals("Got success response while getting one", 200, result.getResponse().getStatus());

		l.info("One User : Content : " + result.getResponse().getContentAsString());

		// Verify that the method is called atleast once
		verify(personRepository).findById(any(String.class));

		Person objPersons = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Person.class);

		l.info("End : PersonControllerTest.testPersonFindById() : " + objPersons.getFirstName());
	}

	private Person stubPersons(int intCtr) {
		List<Company> lstCompanies = Arrays.asList(new Company("Infy" + intCtr, "Mysore" + intCtr));
		int[] arrLocationCoordinates = {1, 2};
		Person objPerson = new Person("Ravi" + intCtr, "Kalla" + intCtr, "IT" + intCtr, arrLocationCoordinates, lstCompanies);
		return objPerson;
	}
}
