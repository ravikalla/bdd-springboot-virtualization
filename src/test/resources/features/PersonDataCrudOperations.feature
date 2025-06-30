Feature: Comprehensive CRUD operations for Person data

@Regression
Scenario: Create a new person with basic information
	Given Database has no persons for jersey flow
	When User creates a person with firstname "John" and lastname "Doe"
	Then The person should be saved successfully
	And Check if there are "1" users in the DB for jersey flow

@Regression
Scenario: Create a person with complete information
	Given Database has no persons for jersey flow
	When User creates a person with complete details
		|FirstName|LastName|Profession     |LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Alice    |Johnson |Product Manager|30       |-97      |TechCorp  |San Francisco      |
	Then The person should be saved successfully
	And Check if there are "1" users in the DB for jersey flow

@Regression
Scenario: Retrieve person by ID
	Given Database has no persons for jersey flow
	And User inserted a person information for jersey flow
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Jane     |Smith   |Engineer  |40       |-74      |DataSoft  |New York           |
	When User retrieves the person by ID
	Then The retrieved person should have firstname "Jane" and lastname "Smith"

@Regression
Scenario: Update person information
	Given Database has no persons for jersey flow
	And User inserted a person information for jersey flow
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Bob      |Wilson  |Analyst   |50       |-80      |InfoTech  |Chicago            |
	When User updates the person's profession to "Senior Analyst"
	Then The person's profession should be updated successfully

@Regression
Scenario: Delete person by ID
	Given Database has no persons for jersey flow
	And User inserted a person information for jersey flow
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Charlie  |Brown   |Manager   |60       |-90      |BizCorp   |Boston             |
	When User deletes the person by ID
	Then The person should be deleted successfully
	And Check if there are "0" users in the DB for jersey flow

@Regression
Scenario: Find persons by first name
	Given Database has no persons for jersey flow
	And User inserted multiple persons with same first name
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|David    |Jones   |Developer |70       |-100     |CodeCorp  |Seattle            |
		|David    |Davis   |Designer  |80       |-110     |DesignLab |Portland           |
	When User searches for persons with firstname "David"
	Then The search should return "2" persons with firstname "David"

@Regression
Scenario: Find persons by last name
	Given Database has no persons for jersey flow
	And User inserted multiple persons with same last name
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg  |CompanyHeadQuarters|
		|Emma     |Taylor  |Architect |90       |-120     |BuildTech   |Denver             |
		|Liam     |Taylor  |Engineer  |100      |-130     |ConstructCo |Austin             |
	When User searches for persons with lastname "Taylor"
	Then The search should return "2" persons with lastname "Taylor"

@Regression
Scenario: Validate empty database state
	Given Database has no persons for jersey flow
	When User retrieves all persons
	Then The result should contain "0" persons

@Regression
Scenario: Multiple create and delete operations
	Given Database has no persons for jersey flow
	When User creates multiple persons
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Grace    |Lee     |Tester    |110      |-140     |TestLab   |Miami              |
		|Henry    |Kim     |Analyst   |120      |-150     |DataFirm  |Phoenix            |
		|Ivy      |Chen    |Manager   |130      |-160     |LeadCorp  |Dallas             |
	Then Check if there are "3" users in the DB for jersey flow
	When User deletes all persons
	Then Check if there are "0" users in the DB for jersey flow

@Regression
Scenario: Handle invalid person data
	Given Database has no persons for jersey flow
	When User attempts to create a person with empty firstname
	Then The creation should fail with validation error

@Regression
Scenario: Handle person retrieval with invalid ID
	Given Database has no persons for jersey flow
	When User attempts to retrieve person with ID "invalid-id-123"
	Then The retrieval should return null or empty result

@Regression
Scenario: Bulk person creation and verification
	Given Database has no persons for jersey flow
	When User creates persons in bulk
		|FirstName|LastName|Profession    |LocationX|LocationY|CompanyOrg    |CompanyHeadQuarters|
		|Oliver   |Garcia  |Software Lead |140      |-170     |SoftwarePro   |San Diego          |
		|Sophia   |Martinez|UI Designer   |150      |-180     |DesignStudio  |Las Vegas          |
		|Noah     |Rodriguez|DevOps       |160      |-190     |CloudTech     |Salt Lake City     |
		|Isabella |Lewis   |QA Lead       |170      |-200     |QualityFirst  |Nashville          |
		|Mason    |Walker  |Architect     |180      |-210     |ArchFirm      |Indianapolis       |
	Then Check if there are "5" users in the DB for jersey flow
	And All persons should have valid IDs assigned
	And All persons should have complete information stored