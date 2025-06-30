package in.ravikalla.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import in.ravikalla.Application;
import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PersonIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonRepository personRepository;

    private String baseUrl;
    private Person testPerson1;
    private Person testPerson2;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port;
        personRepository.deleteAll();
        
        Company company1 = new Company("TechCorp", "San Francisco");
        Company company2 = new Company("DataSoft", "New York");
        
        testPerson1 = new Person("John", "Doe", "Software Engineer", 
                                new int[]{37, -122}, Arrays.asList(company1));
        testPerson2 = new Person("Jane", "Smith", "Data Scientist", 
                                new int[]{40, -74}, Arrays.asList(company2));
    }

    @Test
    public void testGetAllPersons() {
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);

        ResponseEntity<List<Person>> response = restTemplate.exchange(
            baseUrl + "/spring/persons",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Person>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Person> persons = response.getBody();
        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

    @Test
    public void testGetAllPersonsEmpty() {
        ResponseEntity<List<Person>> response = restTemplate.exchange(
            baseUrl + "/spring/persons",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Person>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Person> persons = response.getBody();
        assertNotNull(persons);
        assertTrue(persons.isEmpty());
    }

    @Test
    public void testGetPersonById() {
        Person savedPerson = personRepository.save(testPerson1);
        String personId = savedPerson.getId();

        ResponseEntity<Person> response = restTemplate.getForEntity(
            baseUrl + "/spring/person?id=" + personId,
            Person.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Person person = response.getBody();
        assertNotNull(person);
        assertEquals(personId, person.getId());
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testSavePerson() {
        Person newPerson = new Person("Bob", "Wilson");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> request = new HttpEntity<>(newPerson, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
            baseUrl + "/spring/saveperson",
            request,
            Void.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        List<Person> allPersons = personRepository.findAll();
        assertEquals(1, allPersons.size());
        assertEquals("Bob", allPersons.get(0).getFirstName());
        assertEquals("Wilson", allPersons.get(0).getLastName());
    }

    @Test
    public void testSavePersonWithComplexData() {
        Company company1 = new Company("TechCorp", "San Francisco");
        Company company2 = new Company("StartupInc", "Austin");
        
        Person complexPerson = new Person("Alice", "Johnson", "Product Manager", 
                                        new int[]{30, -97}, Arrays.asList(company1, company2));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> request = new HttpEntity<>(complexPerson, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
            baseUrl + "/spring/saveperson",
            request,
            Void.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        List<Person> allPersons = personRepository.findAll();
        assertEquals(1, allPersons.size());
        Person savedPerson = allPersons.get(0);
        assertEquals("Alice", savedPerson.getFirstName());
        assertEquals("Johnson", savedPerson.getLastName());
        assertEquals("Product Manager", savedPerson.getProfession());
        assertEquals(2, savedPerson.getCompanies().size());
    }

    @Test
    public void testDeleteAll() {
        personRepository.save(testPerson1);
        personRepository.save(testPerson2);
        assertEquals(2, personRepository.findAll().size());

        ResponseEntity<Void> response = restTemplate.getForEntity(
            baseUrl + "/spring/deleteAll",
            Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(personRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() {
        Person savedPerson = personRepository.save(testPerson1);
        String personId = savedPerson.getId();
        assertEquals(1, personRepository.findAll().size());

        ResponseEntity<Void> response = restTemplate.getForEntity(
            baseUrl + "/spring/deleteById?id=" + personId,
            Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(personRepository.findAll().isEmpty());
    }

    @Test
    public void testFullWorkflow() {
        Person person1 = new Person("Alice", "Smith");
        Person person2 = new Person("Bob", "Jones");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<Person> request1 = new HttpEntity<>(person1, headers);
        ResponseEntity<Void> saveResponse1 = restTemplate.postForEntity(
            baseUrl + "/spring/saveperson", request1, Void.class);
        assertEquals(HttpStatus.CREATED, saveResponse1.getStatusCode());
        
        HttpEntity<Person> request2 = new HttpEntity<>(person2, headers);
        ResponseEntity<Void> saveResponse2 = restTemplate.postForEntity(
            baseUrl + "/spring/saveperson", request2, Void.class);
        assertEquals(HttpStatus.CREATED, saveResponse2.getStatusCode());

        ResponseEntity<List<Person>> getResponse = restTemplate.exchange(
            baseUrl + "/spring/persons", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Person>>() {});
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(2, getResponse.getBody().size());

        ResponseEntity<Void> deleteResponse = restTemplate.getForEntity(
            baseUrl + "/spring/deleteAll", Void.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        ResponseEntity<List<Person>> finalGetResponse = restTemplate.exchange(
            baseUrl + "/spring/persons", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Person>>() {});
        assertEquals(HttpStatus.OK, finalGetResponse.getStatusCode());
        assertTrue(finalGetResponse.getBody().isEmpty());
    }
}