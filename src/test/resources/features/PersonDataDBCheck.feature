Feature: EndToEnd test for SpringBoot_MongoDB

@PersonDBChanges
Scenario: Insert and count records
	Given Database has no persons
	And User inserted a person information
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Ravi     |Kalla   |IT        |100|150|AIG USA|Charlotte|
		|Raj      |Kumar   |Auto      |200|250|AIG France|France|
	Then Check if there are "2" users in the database
	
@PersonDBChanges
Scenario: Check if person details are deleted in DB
	Given Delete all persons
	Then Check if there are "0" users in the database

@UselessTests
Scenario: Create CSV file for customizing Hygieia to read custom test results in CSV format
	Given Create "csv" file with name "target/funcTestResults" content "test content"