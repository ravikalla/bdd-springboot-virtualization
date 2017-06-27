package in.ravikalla.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static in.ravikalla.util.BDDAppConstants.URL_EXTERNAL_SITE;
import static in.ravikalla.util.BDDAppConstants.URI_BASE;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_GET_PERSONS;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_SAVE_PERSON;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_PUT_DELETEALL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.expect;

import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.util.BDDUtil;

/**
 * @author ravi kalla
 */
@Component
@Transactional
public class PersonService {
	private Logger l = Logger.getLogger(this.getClass());

	@Autowired
	private PersonRepository personRepository;

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public Person save(final Person person) {
		return personRepository.save(person);
	}

	public Person findById(final String id) {
		return personRepository.findById(id);
	}

	public void deleteAll() {
		personRepository.deleteAll();
	}

	public void deleteById(final String id) {
		personRepository.delete(id);
	}

	public List<Person> findAllWS() {
		l.info("Start : PersonService.findAllWS()");
		RestTemplate restTemplate = new RestTemplate();
		URI uri = UriComponentsBuilder.fromHttpUrl(URL_EXTERNAL_SITE).path(URI_BASE + URI_MONGO_GET_PERSONS).build().toUri();
		final ResponseEntity<String> responseGetPersons = restTemplate.getForEntity(uri, String.class);
		List<Person> lstPersons = BDDUtil.jsonToObject(responseGetPersons.getBody().toString(), new TypeToken<ArrayList<Person>>() {}.getType());
		l.info("End : PersonService.findAllWS()");
		return lstPersons;
	}

	public Person saveWS(final Person person) {
		l.info("Start : PersonService.saveWS(...)");
		String strPersonJSON = BDDUtil.objectToJson(person);
		Response objResponse = expect().given()
				.contentType("application/json").accept("application/json").body(strPersonJSON).when().post(URL_EXTERNAL_SITE + URI_BASE + URI_MONGO_SAVE_PERSON);
		Person objPersonInserted = BDDUtil.jsonToObject(objResponse.getBody().asString(), Person.class);
		l.info("End : PersonService.saveWS(...)");
		return objPersonInserted;
	}

	public void deleteAllWS() {
		expect().given().delete(URL_EXTERNAL_SITE + URI_BASE + URI_MONGO_PUT_DELETEALL);
	}
}
