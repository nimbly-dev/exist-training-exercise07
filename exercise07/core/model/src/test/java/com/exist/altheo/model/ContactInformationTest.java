package com.exist.altheo.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class ContactInformationTest extends TestCase {
    private Person person1;
    private Person person2;
    private Person jolibee;
    
    // private SimpleDateFormat ft;
    private ZoneId defaultZoneId;
    private Date dateToday;
    private Date exampleDate;
    
	@Override
	@BeforeEach
	protected void setUp() throws Exception {
        // this.ft = new SimpleDateFormat ("yyyy-MM-dd");
        this.defaultZoneId = ZoneId.systemDefault();
        this.dateToday = new Date();
        this.exampleDate = Date.from(LocalDate.of(2012, 12, 31).atStartOfDay(defaultZoneId).toInstant());

        List<Role> testRoles = new ArrayList<Role>();
        Set<ContactInformation> testContact = new HashSet<ContactInformation>();
		this.person1 = new Person(1.25, "322", "John", "Doe", "Doo", "Jr.", "The Third", 
        "Doobi Manila", dateToday, true);

        this.person2 = new Person(5, "322", "Scooby", "Doobo", "Doo", "Sr.", 
        "Dog", "Makati", dateToday, true);
        
        this.jolibee =  new Person(3, "52", "Bida", "Ang", "Saya", "Bumblee", "Bee", 
        "Brasil Brazil", exampleDate, true);
	}

    @Test
    public void test_contact_information_getters() {
        ContactInformation contactInformation1 = new ContactInformation(
            "1111", "2222-3333", "hotdigitydog@gmail.com", person1);

        assertTrue(contactInformation1.getLandline() == "1111");
        assertTrue(contactInformation1.getMobileNumber() == "2222-3333");
        assertTrue(contactInformation1.getEmail() == "hotdigitydog@gmail.com");
        assertTrue(contactInformation1.getPerson().getFirstName() == "John");
    }

    @Test 
    public void test_contact_information_setters(){
        ContactInformation contactInformation = new ContactInformation(
            "4444", "5555", "feedback@jfc.com.ph", person2);

        contactInformation.setLandline("9999");
        contactInformation.setMobileNumber("8-7000");
        contactInformation.setEmail("writeus@ph.mcd.com");
        contactInformation.setPerson(jolibee);

        assertTrue(contactInformation.getLandline() == "9999");
        assertTrue(contactInformation.getMobileNumber() == "8-7000");
        assertTrue(contactInformation.getEmail() == "writeus@ph.mcd.com");
        assertTrue(contactInformation.getPerson().getFirstName()== "Bida");
    }
}
