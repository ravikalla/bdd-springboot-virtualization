package in.ravikalla.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import in.ravikalla.Application;
import in.ravikalla.config.TestMongoConfiguration;

import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;

@SpringBootTest(classes = {Application.class, TestMongoConfiguration.class})
@ActiveProfiles("test")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person testPerson1;
    private Person testPerson2;

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();
        
        Company company1 = new Company("TechCorp", "San Francisco");
        Company company2 = new Company("DataSoft", "New York");
        
        testPerson1 = new Person("John", "Doe", "Software Engineer", 
                                new int[]{37, -122}, Arrays.asList(company1));
        testPerson2 = new Person("Jane", "Smith", "Data Scientist", 
                                new int[]{40, -74}, Arrays.asList(company2));
    }

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    public void testSavePerson() {
        Person savedPerson = personRepository.save(testPerson1);
        
        assertNotNull(savedPerson);
        assertNotNull(savedPerson.getId());
        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Doe", savedPerson.getLastName());
        assertEquals("Software Engineer", savedPerson.getProfession());
    }

    @Test
    public void testFindById() {
        Person savedPerson = personRepository.save(testPerson1);
        String personId = savedPerson.getId();
        
        Person foundPerson = personRepository.findById(personId).orElse(null);
        
        assertNotNull(foundPerson);
        assertEquals(personId, foundPerson.getId());
        assertEquals("John", foundPerson.getFirstName());
        assertEquals("Doe", foundPerson.getLastName());
    }

    @Test
    public void testFindByIdNotFound() {
        Person foundPerson = personRepository.findById("nonexistent-id").orElse(null);
        assertNull(foundPerson);
    }

    @Test
    public void testFindByFirstName() {
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);
        
        Person foundPerson = personRepository.findByFirstName("John");
        
        assertNotNull(foundPerson);
        assertEquals("John", foundPerson.getFirstName());
        assertEquals("Doe", foundPerson.getLastName());
    }

    @Test
    public void testFindByFirstNameNotFound() {
        personRepository.save(testPerson1);
        
        Person foundPerson = personRepository.findByFirstName("NonExistent");
        assertNull(foundPerson);
    }

    @Test
    public void testFindByLastName() {
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);
        
        List<Person> foundPersons = personRepository.findByLastName("Doe");
        
        assertNotNull(foundPersons);
        assertEquals(1, foundPersons.size());
        assertEquals("John", foundPersons.get(0).getFirstName());
    }

    @Test
    public void testFindByLastNameMultipleResults() {
        Person person3 = new Person("Bob", "Doe");
        personRepository.save(testPerson1);
        personRepository.save(person3);
        
        List<Person> foundPersons = personRepository.findByLastName("Doe");
        
        assertNotNull(foundPersons);
        assertEquals(2, foundPersons.size());
    }

    @Test
    public void testFindByLastNameNotFound() {
        personRepository.save(testPerson1);
        
        List<Person> foundPersons = personRepository.findByLastName("NonExistent");
        
        assertNotNull(foundPersons);
        assertTrue(foundPersons.isEmpty());
    }

    @Test
    public void testFindAll() {
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);
        
        List<Person> allPersons = personRepository.findAll();
        
        assertNotNull(allPersons);
        assertEquals(2, allPersons.size());
    }

    @Test
    public void testFindAllEmpty() {
        List<Person> allPersons = personRepository.findAll();
        
        assertNotNull(allPersons);
        assertTrue(allPersons.isEmpty());
    }

    @Test
    public void testDeleteById() {
        Person savedPerson = personRepository.save(testPerson1);
        String personId = savedPerson.getId();
        
        personRepository.deleteById(personId);
        
        Person foundPerson = personRepository.findById(personId).orElse(null);
        assertNull(foundPerson);
    }

    @Test
    public void testDeleteAll() {
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);
        
        assertEquals(2, personRepository.findAll().size());
        
        personRepository.deleteAll();
        
        List<Person> allPersons = personRepository.findAll();
        assertNotNull(allPersons);
        assertTrue(allPersons.isEmpty());
    }

    @Test
    public void testPersonWithComplexData() {
        Company company1 = new Company("TechCorp", "San Francisco");
        Company company2 = new Company("StartupInc", "Austin");
        
        Person complexPerson = new Person("Alice", "Johnson", "Product Manager", 
                                        new int[]{30, -97}, Arrays.asList(company1, company2));
        
        Person savedPerson = personRepository.save(complexPerson);
        
        assertNotNull(savedPerson);
        assertNotNull(savedPerson.getId());
        assertEquals("Alice", savedPerson.getFirstName());
        assertEquals("Johnson", savedPerson.getLastName());
        assertEquals("Product Manager", savedPerson.getProfession());
        assertEquals(2, savedPerson.getCompanies().size());
        assertEquals("TechCorp", savedPerson.getCompanies().get(0).getOrgName());
        assertEquals("StartupInc", savedPerson.getCompanies().get(1).getOrgName());
    }
}