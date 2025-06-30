package in.ravikalla.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.ravikalla.bean.Person;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<Person, String> {
	public Person findByFirstName(String firstName);

	public List<Person> findByLastName(String lastName);

	// These methods are inherited from MongoRepository, but explicitly declared for clarity
	@Override
	public List<Person> findAll();

	@Override
	public Optional<Person> findById(String id);

	@Override
	public Person save(Person objPerson);
}
