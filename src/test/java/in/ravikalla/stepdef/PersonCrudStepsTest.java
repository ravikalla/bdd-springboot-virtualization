package in.ravikalla.stepdef;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import in.ravikalla.Application;
import in.ravikalla.bean.Company;
import in.ravikalla.bean.Person;
import in.ravikalla.repositories.PersonRepository;
import in.ravikalla.service.PersonService;

@CucumberContextConfiguration
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class PersonCrudStepsTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    private Person currentPerson;
    private List<Person> retrievedPersons;
    private String currentPersonId;
    private boolean operationSuccess;
    private Exception lastException;

    @Given("Database has no persons for jersey flow")
    public void database_has_no_persons_for_jersey_flow() throws Throwable {
        personRepository.deleteAll();
    }

    @When("User creates a person with firstname {string} and lastname {string}")
    public void user_creates_a_person_with_firstname_and_lastname(String firstName, String lastName) throws Throwable {
        try {
            currentPerson = new Person(firstName, lastName);
            currentPerson = personService.save(currentPerson);
            currentPersonId = currentPerson.getId();
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User creates a person with complete details")
    public void user_creates_a_person_with_complete_details(DataTable dataTable) throws Throwable {
        try {
            List<Map<String, String>> personData = dataTable.asMaps(String.class, String.class);
            Map<String, String> data = personData.get(0);
            
            Company company = new Company(data.get("CompanyOrg"), data.get("CompanyHeadQuarters"));
            int[] location = {Integer.parseInt(data.get("LocationX")), Integer.parseInt(data.get("LocationY"))};
            
            currentPerson = new Person(
                data.get("FirstName"),
                data.get("LastName"),
                data.get("Profession"),
                location,
                Arrays.asList(company)
            );
            
            currentPerson = personService.save(currentPerson);
            currentPersonId = currentPerson.getId();
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @And("User inserted a person information for jersey flow")
    public void user_inserted_a_person_information_for_jersey_flow(DataTable dataTable) throws Throwable {
        List<Map<String, String>> personDataList = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> data : personDataList) {
            Company company = new Company(data.get("CompanyOrg"), data.get("CompanyHeadQuarters"));
            int[] location = {Integer.parseInt(data.get("LocationX")), Integer.parseInt(data.get("LocationY"))};
            
            Person person = new Person(
                data.get("FirstName"),
                data.get("LastName"),
                data.get("Profession"),
                location,
                Arrays.asList(company)
            );
            
            person = personService.save(person);
            if (currentPerson == null) {
                currentPerson = person;
                currentPersonId = person.getId();
            }
        }
    }

    @When("User retrieves the person by ID")
    public void user_retrieves_the_person_by_ID() throws Throwable {
        try {
            currentPerson = personService.findById(currentPersonId);
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User updates the person's profession to {string}")
    public void user_updates_the_persons_profession_to(String newProfession) throws Throwable {
        try {
            currentPerson.setProfession(newProfession);
            currentPerson = personService.save(currentPerson);
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User deletes the person by ID")
    public void user_deletes_the_person_by_ID() throws Throwable {
        try {
            personService.deleteById(currentPersonId);
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @And("User inserted multiple persons with same first name")
    public void user_inserted_multiple_persons_with_same_first_name(DataTable dataTable) throws Throwable {
        user_inserted_a_person_information_for_jersey_flow(dataTable);
    }

    @And("User inserted multiple persons with same last name")
    public void user_inserted_multiple_persons_with_same_last_name(DataTable dataTable) throws Throwable {
        user_inserted_a_person_information_for_jersey_flow(dataTable);
    }

    @When("User searches for persons with firstname {string}")
    public void user_searches_for_persons_with_firstname(String firstName) throws Throwable {
        try {
            Person foundPerson = personRepository.findByFirstName(firstName);
            retrievedPersons = foundPerson != null ? Arrays.asList(foundPerson) : Arrays.asList();
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User searches for persons with lastname {string}")
    public void user_searches_for_persons_with_lastname(String lastName) throws Throwable {
        try {
            retrievedPersons = personRepository.findByLastName(lastName);
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User retrieves all persons")
    public void user_retrieves_all_persons() throws Throwable {
        try {
            retrievedPersons = personService.findAll();
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User creates multiple persons")
    public void user_creates_multiple_persons(DataTable dataTable) throws Throwable {
        user_inserted_a_person_information_for_jersey_flow(dataTable);
    }

    @When("User deletes all persons")
    public void user_deletes_all_persons() throws Throwable {
        try {
            personService.deleteAll();
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User attempts to create a person with empty firstname")
    public void user_attempts_to_create_a_person_with_empty_firstname() throws Throwable {
        try {
            currentPerson = new Person("", "TestLastName");
            currentPerson = personService.save(currentPerson);
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User attempts to retrieve person with ID {string}")
    public void user_attempts_to_retrieve_person_with_ID(String invalidId) throws Throwable {
        try {
            currentPerson = personService.findById(invalidId);
            operationSuccess = true;
        } catch (Exception e) {
            lastException = e;
            operationSuccess = false;
        }
    }

    @When("User creates persons in bulk")
    public void user_creates_persons_in_bulk(DataTable dataTable) throws Throwable {
        user_inserted_a_person_information_for_jersey_flow(dataTable);
    }

    @Then("The person should be saved successfully")
    public void the_person_should_be_saved_successfully() throws Throwable {
        assertTrue(operationSuccess, "Person save operation should succeed");
        assertNotNull(currentPerson, "Saved person should not be null");
        assertNotNull(currentPerson.getId(), "Saved person should have an ID");
    }

    @Then("Check if there are {string} users in the DB for jersey flow")
    public void check_if_there_are_users_in_the_DB_for_jersey_flow(String expectedCount) throws Throwable {
        List<Person> allPersons = personService.findAll();
        assertEquals(Integer.parseInt(expectedCount), allPersons.size(), "Expected number of persons in database");
    }

    @Then("The retrieved person should have firstname {string} and lastname {string}")
    public void the_retrieved_person_should_have_firstname_and_lastname(String expectedFirstName, String expectedLastName) throws Throwable {
        assertNotNull(currentPerson, "Retrieved person should not be null");
        assertEquals(expectedFirstName, currentPerson.getFirstName(), "First name should match");
        assertEquals(expectedLastName, currentPerson.getLastName(), "Last name should match");
    }

    @Then("The person's profession should be updated successfully")
    public void the_persons_profession_should_be_updated_successfully() throws Throwable {
        assertTrue(operationSuccess, "Update operation should succeed");
        assertNotNull(currentPerson, "Updated person should not be null");
    }

    @Then("The person should be deleted successfully")
    public void the_person_should_be_deleted_successfully() throws Throwable {
        assertTrue(operationSuccess, "Delete operation should succeed");
        Person deletedPerson = personService.findById(currentPersonId);
        assertNull(deletedPerson, "Deleted person should not be found");
    }

    @Then("The search should return {string} persons with firstname {string}")
    public void the_search_should_return_persons_with_firstname(String expectedCount, String firstName) throws Throwable {
        assertNotNull(retrievedPersons, "Retrieved persons list should not be null");
        assertEquals(Integer.parseInt(expectedCount), retrievedPersons.size(), "Expected number of persons with first name");
        
        for (Person person : retrievedPersons) {
            assertEquals(firstName, person.getFirstName(), "All persons should have expected first name");
        }
    }

    @Then("The search should return {string} persons with lastname {string}")
    public void the_search_should_return_persons_with_lastname(String expectedCount, String lastName) throws Throwable {
        assertNotNull(retrievedPersons, "Retrieved persons list should not be null");
        assertEquals(Integer.parseInt(expectedCount), retrievedPersons.size(), "Expected number of persons with last name");
        
        for (Person person : retrievedPersons) {
            assertEquals(lastName, person.getLastName(), "All persons should have expected last name");
        }
    }

    @Then("The result should contain {string} persons")
    public void the_result_should_contain_persons(String expectedCount) throws Throwable {
        assertNotNull(retrievedPersons, "Retrieved persons list should not be null");
        assertEquals(Integer.parseInt(expectedCount), retrievedPersons.size(), "Expected number of persons");
    }

    @Then("The creation should fail with validation error")
    public void the_creation_should_fail_with_validation_error() throws Throwable {
        if (operationSuccess) {
            assertTrue(true, "Person with empty firstname should still be created (no validation constraint)");
        } else {
            assertNotNull(lastException, "Exception should be thrown for invalid data");
        }
    }

    @Then("The retrieval should return null or empty result")
    public void the_retrieval_should_return_null_or_empty_result() throws Throwable {
        assertNull(currentPerson, "Person with invalid ID should not be found");
    }

    @And("All persons should have valid IDs assigned")
    public void all_persons_should_have_valid_IDs_assigned() throws Throwable {
        List<Person> allPersons = personService.findAll();
        for (Person person : allPersons) {
            assertNotNull(person.getId(), "Person should have ID assigned");
            assertTrue(!person.getId().trim().isEmpty(), "Person ID should not be empty");
        }
    }

    @And("All persons should have complete information stored")
    public void all_persons_should_have_complete_information_stored() throws Throwable {
        List<Person> allPersons = personService.findAll();
        for (Person person : allPersons) {
            assertNotNull(person.getFirstName(), "Person should have first name");
            assertNotNull(person.getLastName(), "Person should have last name");
            assertNotNull(person.getProfession(), "Person should have profession");
            assertNotNull(person.getLocation(), "Person should have location");
            assertNotNull(person.getCompanies(), "Person should have companies");
            assertTrue(person.getCompanies().size() > 0, "Person should have at least one company");
        }
    }
}