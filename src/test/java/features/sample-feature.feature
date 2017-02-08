Feature: EndToEnd test for SpringBoot_MongoDB

@StartingScenario
Scenario Outline: Starting scenario description
	When Step starting desc "<ScenarioNumber>", "<FirstName>", "<LastName>"
	Examples:
	|ScenarioNumber|FirstName|LastName|
	|1             |Ravi     |Kalla   |
	|2             |Ravi1    |Kalla1  |

@FirstScenario
Scenario Outline: First scenario description
	When Step one desc "<ScenarioNumber>", "<FirstName>", "<LastName>"
	Examples:
	|ScenarioNumber|FirstName|LastName|
	|1             |Ravi     |Kalla   |
	|2             |Ravi1    |Kalla1  |

@SecondScenario
Scenario Outline: Second scenario description
	When Step two desc "<ScenarioNumber>", "<FirstName>", "<LastName>"
	Examples:
	|ScenarioNumber|FirstName|LastName|
	|1             |Ravi     |Kalla   |
	|2             |Ravi1    |Kalla1  |
	