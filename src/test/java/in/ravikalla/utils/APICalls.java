package in.ravikalla.utils;

import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.expect;

import static in.ravikalla.util.BDDAppConstants.URI_BASE;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_GET_PERSONS;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_GET_PERSON_BY_NAME;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_SAVE_PERSON;
import static in.ravikalla.util.BDDAppConstants.URI_MONGO_PUT_DELETEALL;
import static in.ravikalla.util.BDDAppConstants.URI_EXTERNALSITE_GET_PERSONS;
import static in.ravikalla.util.BDDAppConstants.URI_EXTERNALSITE_SAVE_PERSON;
import static in.ravikalla.util.BDDAppConstants.URI_EXTERNALSITE_PUT_DELETEALL;

import com.jayway.restassured.RestAssured;

public class APICalls {
	public static Response getAllPersonsFromDB() {
		Response objResponse = expect().given().port(RestAssured.port).when().get(URI_BASE + URI_MONGO_GET_PERSONS);
		return objResponse;
	}
	public static Response savePersonToDB(String strPerson5JSON) {
		Response objResponse = expect().given().port(RestAssured.port)
				.contentType("application/json").accept("application/json").body(strPerson5JSON).when().post(URI_BASE + URI_MONGO_SAVE_PERSON);
		return objResponse;
	}
	public static Response deleteAllPersonsFromDB() {
		return expect().given().port(RestAssured.port).delete(URI_BASE + URI_MONGO_PUT_DELETEALL);
	}
	public static Response getPersonByNameFromDB(String strFirstName, String strLastName) {
		Response objResponse = null;
		String strURI = URI_BASE + URI_MONGO_GET_PERSON_BY_NAME.toString().replaceAll("<firstName>", strFirstName);
		strURI = strURI.replaceAll("<lastName>", strLastName);
		objResponse =  expect().given().port(RestAssured.port).when().contentType("application/json").get(strURI);
		return objResponse;
	}
	public static Response getAllPersonsFromREST() {
		return expect().given().port(RestAssured.port).when().get(URI_BASE + URI_EXTERNALSITE_GET_PERSONS);
	}
	public static Response savePersonsToREST(String strPerson5JSON) {
		return expect().given().port(RestAssured.port)
				.contentType("application/json").accept("application/json").body(strPerson5JSON).when().post(URI_BASE + URI_EXTERNALSITE_SAVE_PERSON);
	}
	public static Response deleteAllPersonsFromREST() {
		return expect().given().port(RestAssured.port).get(URI_BASE + URI_EXTERNALSITE_PUT_DELETEALL);
	}
}
