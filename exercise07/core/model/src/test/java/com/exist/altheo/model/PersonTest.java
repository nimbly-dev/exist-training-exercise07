package com.exist.altheo.model;


import java.text.SimpleDateFormat;
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

public class PersonTest extends TestCase {

    private Person person2;

    private SimpleDateFormat ft;
    private ZoneId defaultZoneId;
    private Date dateToday;
    private Date exampleDate;

    List<Role> testRoles;
    Set<ContactInformation> testContact;

	@Override
	@BeforeEach
	protected void setUp() throws Exception {
        this.ft = new SimpleDateFormat ("yyyy-MM-dd");
        this.defaultZoneId = ZoneId.systemDefault();
        this.dateToday = new Date();
        this.exampleDate = Date.from(LocalDate.of(2012, 12, 31).atStartOfDay(defaultZoneId).toInstant());

        this.testRoles = new ArrayList<Role>();
        this.testContact = new HashSet<>();

        Role adminRole = new Role("Admin");
        Role hackerRole = new Role("Hacker");

        this.testRoles.add(adminRole);
        this.testRoles.add(hackerRole);

        ContactInformation contactInformation = new ContactInformation(
            "4444", "5555", "feedback@jfc.com.ph", person2);

        this.testContact.add(contactInformation);            

	}

    
    @Test
    public void test_person_getters() {

        Person person = 
        new Person(1.25, "322", "Scoobi Doobi Doo", "Doobi Manila", dateToday, 
        true, testContact, testRoles);
        // new Person("John", "Doe", "Doo", "Jr", "King", 1.25, "7100", 
        // "Doobi", "Manila", "322", dateToday, true, testContact, 
        // testRoles);

        // assertTrue(person.getFirstName() == "John");
        // assertTrue(person.getLastName() == "Doe");
        // assertTrue(person.getMiddleName() == "Doo");
        // assertTrue(person.getSuffix() == "Jr");
        // assertTrue(person.getTitle() == "King");
        
        assertTrue(person.getGwa() == 1.25);

        // assertTrue(person.getStreetNum() == "7100");
        // assertTrue(person.getBarangay() == "Doobi");
        // assertTrue(person.getCity() == "Manila");
        assertTrue(person.getZipCode() == "322");

        assertTrue(person.getName() == "Scoobi Doobi Doo");
        assertTrue(person.getAddress() == "Doobi Manila");

        // assertTrue(person.getTitle() == "King");

        assertEquals(person.getDateHired(), dateToday);

        assertTrue(person.getIsCurrentlyEmployed() == true);
        assertTrue(person.getContactInformations() != null);
        assertTrue(person.getRoles().size() == 2);
    }

    @Test
    public void test_person_setters(){

        Person person =  
        new Person(1.25, "322", "Scoobi Doobi Doo", "Doobi Manila", dateToday, 
        true, testContact, testRoles);

        List<Role> roles1 = new ArrayList<Role>();

        Role mascotRole = new Role("Mascot");

        roles1.add(mascotRole);

        Set<ContactInformation> testContact1 =  new HashSet<>();

        ContactInformation contactInformation = new ContactInformation(
            "4444", "8-7000", "rcbcstocktransfer@rcbc.com", person2);

        testContact1.add(contactInformation);
        
        // person.setFirstName("Bida");
        // person.setLastName("Ang");
        // person.setMiddleName("Saya");
        // person.setSuffix("Bumble");
        // person.setTitle("Bee");
        person.setName("Bida And Saya");
        person.setAddress("Manila City");
        person.setDateHired(exampleDate);
        person.setIsCurrentlyEmployed(false);
        person.setContactInformations(testContact1);
        person.setRoles(roles1);

        // person.setBarangay("Lobi");
        // person.setStreetNum("91");
        // person.setCity("Loki");
        person.setGwa(1.50);
        person.setZipCode("423");
        

        // assertTrue(person.getFirstName() == "Bida");
        // assertTrue(person.getLastName() == "Ang");
        // assertTrue(person.getMiddleName() == "Saya");
        // assertTrue(person.getSuffix() == "Bumble");
        // assertTrue(person.getTitle() == "Bee");
        assertTrue(person.getName() == "Bida And Saya");
        assertTrue(person.getAddress() == "Manila City");
        assertEquals(person.getDateHired(), exampleDate);
        assertTrue(person.getIsCurrentlyEmployed() == false);
        assertTrue(person.getContactInformations() != null);
        assertTrue(person.getRoles().size() == 1);

        // assertTrue(person.getBarangay() == "Lobi");
        // assertTrue(person.getStreetNum() == "91");
        // assertTrue(person.getCity() == "Loki");
        assertTrue(person.getGwa() == 1.50);
        assertTrue(person.getZipCode() == "423");
    }
}
