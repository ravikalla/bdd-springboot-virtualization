package in.ravikalla.controllers;

import in.ravikalla.config.TestConfiguration;

import java.net.URI;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @author ravi kalla
 * @see <a href="http://www.bytestree.com/">BytesTree</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfiguration.class})
@WebAppConfiguration
public class PersonControllerTest2 {

	private static Log l = LogFactory.getLog(PersonControllerTest2.class);
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private final String URL_PERSONS = "/persons";
	
	@Before
	public void setUp(){
	  mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test() throws Exception{
	  MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_PERSONS).contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
    int status = result.getResponse().getStatus();
    l.info("Status is : " + status);
	}
}
