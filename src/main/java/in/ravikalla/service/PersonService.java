package in.ravikalla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;

/**
 * @author ravi kalla
 */
@Component
@Transactional
public class PersonService {
	@Autowired
	private PersonRepository personRepository;

//	@Autowired
//	public PersonService(final PersonRepository personRepository) {
//		this.personRepository = personRepository;
//	}

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
}
