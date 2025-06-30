package in.ravikalla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person testPerson1;
    private Person testPerson2;
    private List<Person> testPersons;

    @BeforeEach
    public void setUp() {
        Company company1 = new Company("TechCorp", "San Francisco");
        Company company2 = new Company("DataSoft", "New York");
        
        testPerson1 = new Person("John", "Doe", "Software Engineer", 
                                new int[]{37, -122}, Arrays.asList(company1));
        testPerson1.setId("1");
        
        testPerson2 = new Person("Jane", "Smith", "Data Scientist", 
                                new int[]{40, -74}, Arrays.asList(company2));
        testPerson2.setId("2");
        
        testPersons = Arrays.asList(testPerson1, testPerson2);
    }

    @Test
    public void testFindAll() {
        when(personRepository.findAll()).thenReturn(testPersons);
        
        List<Person> result = personService.findAll();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllEmpty() {
        when(personRepository.findAll()).thenReturn(Arrays.asList());
        
        List<Person> result = personService.findAll();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        when(personRepository.save(any(Person.class))).thenReturn(testPerson1);
        
        Person result = personService.save(testPerson1);
        
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        
        verify(personRepository, times(1)).save(testPerson1);
    }

    @Test
    public void testSaveNewPerson() {
        Person newPerson = new Person("Bob", "Wilson");
        Person savedPerson = new Person("Bob", "Wilson");
        savedPerson.setId("3");
        
        when(personRepository.save(newPerson)).thenReturn(savedPerson);
        
        Person result = personService.save(newPerson);
        
        assertNotNull(result);
        assertEquals("3", result.getId());
        assertEquals("Bob", result.getFirstName());
        assertEquals("Wilson", result.getLastName());
        
        verify(personRepository, times(1)).save(newPerson);
    }

    @Test
    public void testFindById() {
        when(personRepository.findById("1")).thenReturn(Optional.of(testPerson1));
        
        Person result = personService.findById("1");
        
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        
        verify(personRepository, times(1)).findById("1");
    }

    @Test
    public void testFindByIdNotFound() {
        when(personRepository.findById("999")).thenReturn(Optional.empty());
        
        Person result = personService.findById("999");
        
        assertEquals(null, result);
        
        verify(personRepository, times(1)).findById("999");
    }

    @Test
    public void testDeleteAll() {
        doNothing().when(personRepository).deleteAll();
        
        personService.deleteAll();
        
        verify(personRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteById() {
        doNothing().when(personRepository).deleteById(anyString());
        
        personService.deleteById("1");
        
        verify(personRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteByIdWithNullId() {
        doNothing().when(personRepository).deleteById(isNull());
        
        personService.deleteById(null);
        
        verify(personRepository, times(1)).deleteById((String) null);
    }

    @Test
    public void testDeleteByIdWithEmptyId() {
        doNothing().when(personRepository).deleteById(anyString());
        
        personService.deleteById("");
        
        verify(personRepository, times(1)).deleteById("");
    }

    @Test
    public void testSavePersonWithComplexData() {
        Company company1 = new Company("TechCorp", "San Francisco");
        Company company2 = new Company("StartupInc", "Austin");
        
        Person complexPerson = new Person("Alice", "Johnson", "Product Manager", 
                                        new int[]{30, -97}, Arrays.asList(company1, company2));
        
        Person savedComplexPerson = new Person("Alice", "Johnson", "Product Manager", 
                                              new int[]{30, -97}, Arrays.asList(company1, company2));
        savedComplexPerson.setId("complex-1");
        
        when(personRepository.save(complexPerson)).thenReturn(savedComplexPerson);
        
        Person result = personService.save(complexPerson);
        
        assertNotNull(result);
        assertEquals("complex-1", result.getId());
        assertEquals("Alice", result.getFirstName());
        assertEquals("Johnson", result.getLastName());
        assertEquals("Product Manager", result.getProfession());
        assertEquals(2, result.getCompanies().size());
        assertEquals("TechCorp", result.getCompanies().get(0).getOrgName());
        assertEquals("StartupInc", result.getCompanies().get(1).getOrgName());
        
        verify(personRepository, times(1)).save(complexPerson);
    }

    @Test
    public void testSavePersonWithNullValues() {
        Person personWithNulls = new Person();
        personWithNulls.setFirstName("Test");
        
        Person savedPersonWithNulls = new Person();
        savedPersonWithNulls.setId("null-test");
        savedPersonWithNulls.setFirstName("Test");
        
        when(personRepository.save(personWithNulls)).thenReturn(savedPersonWithNulls);
        
        Person result = personService.save(personWithNulls);
        
        assertNotNull(result);
        assertEquals("null-test", result.getId());
        assertEquals("Test", result.getFirstName());
        
        verify(personRepository, times(1)).save(personWithNulls);
    }
}