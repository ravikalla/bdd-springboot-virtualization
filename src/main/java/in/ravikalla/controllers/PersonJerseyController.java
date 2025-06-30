package in.ravikalla.controllers;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import in.ravikalla.bean.Person;
import in.ravikalla.service.PersonService;
import in.ravikalla.util.BDDAppConstants;

/**
 * @author ravi kalla
 *
 */
@Path(BDDAppConstants.URI_BASE)
@Produces({MediaType.APPLICATION_JSON})
@Component
public class PersonJerseyController {
	private static final Logger l = LoggerFactory.getLogger(PersonJerseyController.class);

	@Autowired
	private PersonService personService;

	@Path(BDDAppConstants.URI_MONGO_GET_PERSONS)
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Person> findAll() {
		l.info("46: Start : PersonJerseyController.findAll()");
		List<Person> findAll = personService.findAll();
		l.info("48: Start : PersonJerseyController.findAll()");
		return findAll;
	}

	@Path(BDDAppConstants.URI_MONGO_GET_PERSON)
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Person findById(@QueryParam("id") String id) {
		return personService.findById(id);
	}

	@Path(BDDAppConstants.URI_MONGO_PUT_DELETEALL)
	@DELETE
	public void deleteAll() {
		l.info("62: Start : PersonJerseyController.deleteAll()");
		personService.deleteAll();
		l.info("46: Start : PersonJerseyController.deleteAll()");
	}

	@Path(BDDAppConstants.URI_MONGO_PUT_DELETEBYID)
	@DELETE
	public void delete(@QueryParam("id") String id) {
		personService.deleteById(id);
	}

	@Path(BDDAppConstants.URI_MONGO_SAVE_PERSON)
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Person save(@RequestBody final Person person) {
		l.debug("Start : PersonJerseyController.save(...)");

		Person savedPerson = personService.save(person);

//		Link linkOfAPerson = new PersonResourceLnks(savedPerson).getLink("self");

//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setLocation(URI.create(linkOfAPerson.getHref()));

		l.debug("End : PersonJerseyController.save(...)");
//		// httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("person/{id}").buildAndExpand(savedPerson.getId()).toUri());
//		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
		return savedPerson;
	}

	@Path(BDDAppConstants.URI_EXTERNALSITE_GET_PERSONS)
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Person> findAllExternalSite() {
		return personService.findAllWS();
	}

	@Path(BDDAppConstants.URI_EXTERNALSITE_PUT_DELETEALL)
	@GET
	public void deleteAllExternalSite() {
		personService.deleteAllWS();
	}

	@Path(BDDAppConstants.URI_EXTERNALSITE_SAVE_PERSON)
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Person saveExternalSite(@RequestBody final Person person) {
		l.debug("Start : PersonJerseyController.save(...)");

		Person savedPerson = personService.saveWS(person);

		l.debug("End : PersonJerseyController.save(...)");
		return savedPerson;
	}
}