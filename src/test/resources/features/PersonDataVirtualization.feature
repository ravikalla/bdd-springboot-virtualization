Feature: Validate person data virtualization using mocked external services

@RestVirtualization
Scenario: Verify external service response with WireMock
	Given External service is mocked with WireMock
	When User retrieves persons from external service
	Then The response should match expected format
	And The response should contain mocked data

@RestVirtualization
Scenario: Create person via external service with mocked response
	Given External service is mocked for person creation
	When User creates a person via external service
		|FirstName|LastName|Profession|LocationX|LocationY|CompanyOrg|CompanyHeadQuarters|
		|Mock     |User    |Tester    |100      |200      |MockCorp  |Virtual City       |
	Then The external service should return success response
	And The response should contain the created person data

@RestVirtualization
Scenario: Handle external service timeout with virtualization
	Given External service is configured with timeout
	When User attempts to retrieve persons from external service
	Then The request should handle timeout gracefully
	And A timeout error should be returned

@RestVirtualization
Scenario: Verify external service error handling
	Given External service is mocked to return error
	When User attempts to create person via external service
	Then The service should return appropriate error response
	And Error handling should work correctly

@RestVirtualization
Scenario: Multiple external service calls with different responses
	Given External service is mocked with multiple response scenarios
	When User makes multiple calls to external service
		|CallType|ExpectedResponse|
		|GET     |success        |
		|POST    |created        |
		|DELETE  |deleted        |
	Then Each call should return the expected mocked response

@RestVirtualization
Scenario: Validate external service data transformation
	Given External service returns data in different format
	When User retrieves person data from external service
	Then The data should be properly transformed
	And The transformed data should match internal format

@RestVirtualization
Scenario: Test external service fallback mechanism
	Given Primary external service is unavailable
	And Fallback service is mocked and available
	When User attempts to retrieve person data
	Then The system should fallback to secondary service
	And Data should be retrieved successfully from fallback

@RestVirtualization
Scenario: Verify external service authentication handling
	Given External service requires authentication
	And Authentication is mocked to return valid token
	When User makes authenticated request to external service
	Then The request should include proper authentication
	And The service should return authenticated response

@RestVirtualization
Scenario: Test external service rate limiting
	Given External service has rate limiting enabled
	When User makes multiple rapid requests to external service
	Then The service should enforce rate limiting
	And Appropriate rate limit responses should be returned

@RestVirtualization
Scenario: Validate external service data consistency
	Given Multiple external services are mocked
	When User retrieves same person data from different services
	Then All services should return consistent data
	And Data integrity should be maintained across services