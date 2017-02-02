package in.ravikalla.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.ravikalla.bean.Person;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<Person, String> {
	public Person findByFirstName(String firstName);

	public List<Person> findByLastName(String lastName); // List<Person> findByLastName(@Param("name") String name);

	public Person findById(String id);
}
