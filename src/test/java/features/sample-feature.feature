Feature: EndToEnd test for SpringBoot_MongoDB

@FirstScenario
Scenario Outline: First scenario description
	Given Step one desc "<ScenarioNumber>", "<FirstName>", "<LastName>"
	Examples:
	|ScenarioNumber|FirstName|LastName|
	|1             |Ravi     |Kalla   |
	|2             |Ravi1    |Kalla1  |

@SecondScenario
Scenario Outline: Second scenario description
	Given Step two desc "<ScenarioNumber>", "<FirstName>", "<LastName>"
	Examples:
	|ScenarioNumber|FirstName|LastName|
	|1             |Ravi     |Kalla   |
	|2             |Ravi1    |Kalla1  |
