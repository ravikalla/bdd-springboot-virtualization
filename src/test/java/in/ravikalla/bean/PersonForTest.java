package in.ravikalla.bean;

import java.util.Arrays;
import java.util.List;

public class PersonForTest {
	private String firstName;
	private String lastName;
	private String profession;
	private String locationX;
	private String locationY;
	private String companyOrg;
	private String companyHeadQuarters;

	public PersonForTest(String strFirstName, String strLastName, String strProfession, String strLocationX, String strLocationY, String strCompanyOrg, String strCompanyHeadQuarters) {
		this.firstName = strFirstName;
		this.lastName = strLastName;
		this.profession = strProfession;
		this.locationX = strLocationX;
		this.locationY = strLocationY;
		this.companyOrg = strCompanyOrg;
		this.companyHeadQuarters = strCompanyHeadQuarters;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getLocationX() {
		return locationX;
	}
	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}
	public String getLocationY() {
		return locationY;
	}
	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}
	public String getCompanyOrg() {
		return companyOrg;
	}
	public void setCompanyOrg(String companyOrg) {
		this.companyOrg = companyOrg;
	}
	public String getCompanyHeadQuarters() {
		return companyHeadQuarters;
	}
	public void setCompanyHeadQuarters(String companyHeadQuarters) {
		this.companyHeadQuarters = companyHeadQuarters;
	}
	public Person getPerson() {
		List<Company> lstCompanies = Arrays.asList(new Company(companyOrg, companyHeadQuarters));
		int[] arrLocationCoordinates = { Integer.parseInt(locationX), Integer.parseInt(locationY) };
		Person objPerson = new Person(firstName, lastName, profession, arrLocationCoordinates, lstCompanies);
		return objPerson;
	}
}
