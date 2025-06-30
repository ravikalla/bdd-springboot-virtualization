package in.ravikalla.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import in.ravikalla.Application;
import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class PersonRepositoryTestSimple {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSaveAndFindPerson() {
        personRepository.deleteAll();
        
        Company company = new Company("TestCorp", "Test City");
        Person testPerson = new Person("John", "Doe", "Tester", 
                                     new int[]{100, 200}, Arrays.asList(company));
        
        Person savedPerson = personRepository.save(testPerson);
        
        assertNotNull("Person should be saved", savedPerson);
        assertNotNull("Person should have ID", savedPerson.getId());
        assertEquals("First name should match", "John", savedPerson.getFirstName());
        assertEquals("Last name should match", "Doe", savedPerson.getLastName());
        
        List<Person> allPersons = personRepository.findAll();
        assertEquals("Should have one person", 1, allPersons.size());
    }

    @Test
    public void testFindByFirstName() {
        personRepository.deleteAll();
        
        Person person = new Person("Jane", "Smith");
        personRepository.save(person);
        
        Person found = personRepository.findByFirstName("Jane");
        assertNotNull("Should find person by first name", found);
        assertEquals("First name should match", "Jane", found.getFirstName());
        assertEquals("Last name should match", "Smith", found.getLastName());
    }

    @Test
    public void testFindByLastName() {
        personRepository.deleteAll();
        
        Person person1 = new Person("Bob", "Johnson");
        Person person2 = new Person("Alice", "Johnson");
        personRepository.save(person1);
        personRepository.save(person2);
        
        List<Person> johnsons = personRepository.findByLastName("Johnson");
        assertEquals("Should find two persons with last name Johnson", 2, johnsons.size());
    }

    @Test
    public void testDeleteAll() {
        personRepository.deleteAll();
        
        Person person = new Person("Test", "User");
        personRepository.save(person);
        assertEquals("Should have one person", 1, personRepository.findAll().size());
        
        personRepository.deleteAll();
        assertEquals("Should have no persons after deleteAll", 0, personRepository.findAll().size());
    }
}