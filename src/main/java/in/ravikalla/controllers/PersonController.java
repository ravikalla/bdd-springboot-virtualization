package in.ravikalla.controllers;

import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.ravikalla.bean.Person;
import in.ravikalla.linkcollectors.PersonResourceLnks;
import in.ravikalla.service.PersonService;

/**
 * @author ravi kalla
 *
 */
@RestController
@Component
public class PersonController {
	Logger l = Logger.getLogger(this.getClass());

	@Autowired
	private PersonService personService;

//	@Autowired
//	PersonController(final PersonService personService) {
//		this.personService = personService;
//	}

	@RequestMapping("/persons")
	public List<Person> findAll() {
		return personService.findAll();
	}

	@RequestMapping("/person")
	public Person findById(@RequestParam(value = "id") final String id) {
		return personService.findById(id);
	}

	@RequestMapping("/deleteAll")
	public void deleteAll() {
		personService.deleteAll();
	}

	@RequestMapping("/deleteById")
	public void deleteAll(@RequestParam(value = "id") final String id) {
		personService.deleteById(id);
	}

	@RequestMapping(value = "/saveperson", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> save(@RequestBody final Person person) {
		l.debug("Start : PersonController.save(...)");

		Person savedPerson = personService.save(person);

		Link linkOfAPerson = new PersonResourceLnks(savedPerson).getLink("self");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(URI.create(linkOfAPerson.getHref()));

		l.debug("End : PersonController.save(...)");
		// httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("person/{id}").buildAndExpand(savedPerson.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
}